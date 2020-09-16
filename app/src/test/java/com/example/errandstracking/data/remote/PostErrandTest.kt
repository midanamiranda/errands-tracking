package com.example.errandstracking.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.errandstracking.data.Result.*
import com.example.errandstracking.data.remote.api.ErrandsTrackingService
import com.example.errandstracking.data.remote.api.ErrandsTrackingServiceHelper
import com.example.errandstracking.data.remote.api.ErrandsTrackingServiceHelperImpl
import com.example.errandstracking.data.remote.api.RetrofitBuilder
import com.example.errandstracking.data.remote.models.PostErrandRequestBody
import com.example.errandstracking.data.remote.models.PostErrandResponse
import com.example.errandstracking.utils.ErrandsServiceTestUtils
import com.example.errandstracking.utils.USER_ID
import com.example.errandstracking.utils.postErrandResponsePath
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

class PostErrandTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer : MockWebServer

    private lateinit var errandsTrackingService: ErrandsTrackingService

    private lateinit var errandsTrackingServiceHelper : ErrandsTrackingServiceHelper

    private val dummyErrandTitle = "My Dummy Post Request Body"

    private val postErrandRequestBody = PostErrandRequestBody(
        false,
        dummyErrandTitle,
        USER_ID
    )

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
    fun `post errands dummy response json returns valid object`() {
        val dummyResponse = ErrandsServiceTestUtils.postErrandResponseTestObject()
        assertThat(dummyResponse)
            .isNotNull
            .hasNoNullFieldsOrProperties()
            .isInstanceOf(PostErrandResponse::class.java)
    }

    @Test
    fun `successful post errand from service returns valid response object`() {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(ErrandsServiceTestUtils.getJson(postErrandResponsePath))
                .setResponseCode(HttpURLConnection.HTTP_OK)
        )

        val result = runBlocking { errandsTrackingService.postErrand(postErrandRequestBody) }

        TestCase.assertTrue(result.isSuccessful)

        assertThat(result.body())
            .isNotNull
            .hasNoNullFieldsOrProperties()
            .hasFieldOrPropertyWithValue("title", dummyErrandTitle)
            .hasFieldOrPropertyWithValue("userId", USER_ID)
    }

    @Test
    fun `successful post errand from service helper returns successful result`() {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(ErrandsServiceTestUtils.getJson(postErrandResponsePath))
                .setResponseCode(HttpURLConnection.HTTP_OK)
        )

        errandsTrackingServiceHelper = ErrandsTrackingServiceHelperImpl(
            errandsTrackingService
        )

        val errand = runBlocking { errandsTrackingServiceHelper.postErrand(postErrandRequestBody) }

        TestCase.assertTrue(errand is Success)

        errand as Success

        assertThat(errand.data)
            .isNotNull
            .hasNoNullFieldsOrProperties()
            .hasFieldOrPropertyWithValue("title", dummyErrandTitle)
            .hasFieldOrPropertyWithValue("userId", USER_ID)
    }

    @Test
    fun `failed post errand from service helper returns error result`() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        )

        errandsTrackingServiceHelper = ErrandsTrackingServiceHelperImpl(
            errandsTrackingService
        )

        val errand = runBlocking { errandsTrackingServiceHelper.postErrand(postErrandRequestBody) }

        TestCase.assertTrue(errand is Error)

        errand as Error

        assertThat(errand.exception)
            .isInstanceOf(HttpException::class.java)
            .hasFieldOrPropertyWithValue("code", HttpURLConnection.HTTP_INTERNAL_ERROR)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }










}