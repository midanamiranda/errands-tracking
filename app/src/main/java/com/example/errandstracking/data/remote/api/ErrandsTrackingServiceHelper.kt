package com.example.errandstracking.data.remote.api

import com.example.errandstracking.data.remote.models.GetAllErrandsResponseItem
import com.example.errandstracking.data.Result

interface ErrandsTrackingServiceHelper {
    suspend fun getAllUsersErrands(userId: Int) : Result<List<GetAllErrandsResponseItem>>
}
