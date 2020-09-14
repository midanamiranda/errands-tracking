package com.example.errandstracking.data.remote.api

import com.example.errandstracking.custom.EmptyQueryResultException
import com.example.errandstracking.data.Result
import com.example.errandstracking.data.Result.*
import com.example.errandstracking.data.remote.models.GetAllErrandsResponseItem
import retrofit2.HttpException

class ErrandsTrackingServiceHelperImpl(
    private val errandsTrackingService: ErrandsTrackingService
) : ErrandsTrackingServiceHelper {

    override suspend fun getAllUsersErrands(userId: Int): Result<List<GetAllErrandsResponseItem>> {
        val result = errandsTrackingService.getAllUserErrands(userId)

        return when (result.isSuccessful) {
            false -> Error(HttpException(result))
            true -> {
                when (result.body()!!.isNullOrEmpty()) {
                    true -> Error(EmptyQueryResultException("No errands available for required user."))
                    false -> Success(result.body()!!)
                }
            }
        }
    }
}
