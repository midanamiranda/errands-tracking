package com.example.errandstracking.data.remote.api

import com.example.errandstracking.custom.EmptyQueryResultException
import com.example.errandstracking.data.Result
import com.example.errandstracking.data.Result.*
import com.example.errandstracking.data.remote.models.GetAllErrandsResponseItem
import com.example.errandstracking.data.remote.models.PostErrandRequestBody
import com.example.errandstracking.data.remote.models.PostErrandResponse
import retrofit2.HttpException

class ErrandsTrackingServiceHelperImpl(
    private val errandsTrackingService: ErrandsTrackingService
) : ErrandsTrackingServiceHelper {

    override suspend fun getAllUsersErrands(userId: Int): Result<List<GetAllErrandsResponseItem>> {
        val result = try {
            errandsTrackingService.getAllUserErrands(userId)
        } catch (ex: Exception) {
            return Error(ex)
        }

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

    override suspend fun postErrand(postErrandRequestBody: PostErrandRequestBody): Result<PostErrandResponse> {
        val result = try {
            errandsTrackingService.postErrand(postErrandRequestBody)
        } catch (ex: Exception) {
            return Error(ex)
        }

        return when (result.isSuccessful) {
            false -> Error(HttpException(result))
            true -> Success(result.body()!!)
        }
    }
}
