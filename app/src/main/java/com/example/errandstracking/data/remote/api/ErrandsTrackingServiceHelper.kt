package com.example.errandstracking.data.remote.api

import com.example.errandstracking.data.remote.models.GetAllErrandsResponseItem
import com.example.errandstracking.data.Result
import com.example.errandstracking.data.remote.models.PostErrandRequestBody
import com.example.errandstracking.data.remote.models.PostErrandResponse

interface ErrandsTrackingServiceHelper {
    suspend fun getAllUsersErrands(userId: Int) : Result<List<GetAllErrandsResponseItem>>
    suspend fun postErrand(postErrandRequestBody: PostErrandRequestBody): Result<PostErrandResponse>
}
