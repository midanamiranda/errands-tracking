package com.example.errandstracking.data.remote.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    fun getRetrofit(baseUrl: String?, client: OkHttpClient?): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(baseUrl ?: BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        if (client != null) builder.client(client)

        return builder.build()
    }

}
