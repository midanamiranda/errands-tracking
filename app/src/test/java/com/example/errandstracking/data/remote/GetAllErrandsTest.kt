package com.example.errandstracking.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.errandstracking.custom.EmptyQueryResultException
import com.example.errandstracking.data.Result.*
import com.example.errandstracking.data.remote.api.ErrandsTrackingService
import com.example.errandstracking.data.remote.api.ErrandsTrackingServiceHelper
import com.example.errandstracking.data.remote.api.ErrandsTrackingServiceHelperImpl
import com.example.errandstracking.data.remote.api.RetrofitBuilder
import com.example.errandstracking.utils.ErrandsServiceTestUtils
import com.example.errandstracking.utils.USER_ID
import com.example.errandstracking.utils.getAllErrandsEmptyResponsePath
import com.example.errandstracking.utils.getAllErrandsResponsePath
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import java.net.HttpURLConnection

class GetAllErrandsTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer : MockWebServer

    private lateinit var errandsTrackingService: ErrandsTrackingService

    private lateinit var errandsTrackingServiceHelper : ErrandsTrackingServiceHelper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mockWebServer = MockWebServer()
        mockWebServer.start()

        errandsTrackingService = RetrofitBuilder.getRetrofit(
            mockWebServer.url("/").toString(),
            null
        ).create(ErrandsTrackingService::class.java)
    }

    @Test
    fun `get all errands dummy response json returns valid errands list`() {
        val errandsListResponse = ErrandsServiceTestUtils.getAllErrandsResponseTestObject()
        TestCase.assertNotNull(errandsListResponse)
        TestCase.assertFalse(errandsListResponse.isNullOrEmpty())
    }

    @Test
    fun `successful get all errands from service returns valid response object`() {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(ErrandsServiceTestUtils.getJson(getAllErrandsResponsePath))
                .setResponseCode(HttpURLConnection.HTTP_OK)
        )

        val result = runBlocking { errandsTrackingService.getAllUserErrands(USER_ID) }

        TestCase.assertTrue(result.isSuccessful)

        assertThat(result.body()!!)
            .hasSize(ErrandsServiceTestUtils.getAllErrandsResponseTestObject().size)
            .allMatch { errand -> errand.userId == USER_ID }
    }

    @Test
    fun `successful get all errands from service helper returns successful result`() {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(ErrandsServiceTestUtils.getJson(getAllErrandsResponsePath))
                .setResponseCode(HttpURLConnection.HTTP_OK)
        )

        errandsTrackingServiceHelper = ErrandsTrackingServiceHelperImpl(
            errandsTrackingService
        )

        val errandsListResult = runBlocking { errandsTrackingServiceHelper.getAllUsersErrands(USER_ID) }

        TestCase.assertTrue(errandsListResult is Success)

        errandsListResult as Success

        assertThat(errandsListResult.data)
            .hasSize(ErrandsServiceTestUtils.getAllErrandsResponseTestObject().size)
            .allMatch { errand -> errand.userId == USER_ID }
    }

    @Test
    fun `successful get all errands from service helper with empty list returns empty query result exception`() {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(ErrandsServiceTestUtils.getJson(getAllErrandsEmptyResponsePath))
                .setResponseCode(HttpURLConnection.HTTP_OK)
        )

        errandsTrackingServiceHelper = ErrandsTrackingServiceHelperImpl(
            errandsTrackingService
        )

        val errandsListResult = runBlocking { errandsTrackingServiceHelper.getAllUsersErrands(USER_ID) }

        TestCase.assertTrue(errandsListResult is Error)

        errandsListResult as Error

        assertThat(errandsListResult.exception)
            .isInstanceOf(EmptyQueryResultException::class.java)
            .hasMessage("No errands available for required user.")
    }

    @Test
    fun `failed get all errands from service helper returns http exception with correct code`() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        )

        errandsTrackingServiceHelper = ErrandsTrackingServiceHelperImpl(
            errandsTrackingService
        )

        val errandsListResult = runBlocking { errandsTrackingServiceHelper.getAllUsersErrands(USER_ID) }

        TestCase.assertTrue(errandsListResult is Error)

        errandsListResult as Error

        assertThat(errandsListResult.exception)
            .isInstanceOf(HttpException::class.java)
            .hasFieldOrPropertyWithValue("code", HttpURLConnection.HTTP_INTERNAL_ERROR)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}