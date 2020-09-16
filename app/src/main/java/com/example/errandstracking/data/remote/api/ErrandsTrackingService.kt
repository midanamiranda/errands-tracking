package com.example.errandstracking.data.remote.api

import com.example.errandstracking.data.remote.Endpoints
import com.example.errandstracking.data.remote.USER_ID_QUERY
import com.example.errandstracking.data.remote.models.GetAllErrandsResponse
import com.example.errandstracking.data.remote.models.PostErrandRequestBody
import com.example.errandstracking.data.remote.models.PostErrandResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ErrandsTrackingService {
    @GET(Endpoints.GET_ALL_ERRANDS_PATH)
    suspend fun getAllUserErrands(@Query(USER_ID_QUERY) userId: Int): Response<GetAllErrandsResponse>

    @POST(Endpoints.POST_ERRAND_PATH)
    suspend fun postErrand(@Body postErrandRequestBody: PostErrandRequestBody): Response<PostErrandResponse>

}
