package com.example.errandstracking.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response

private const val ACCEPT = "Accept"
private const val CONTENT_TYPE = "Content-Type"
private const val APPLICATION_JSON = "application/json"

class MediaTypeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(ACCEPT, APPLICATION_JSON)
            .addHeader(CONTENT_TYPE, APPLICATION_JSON)
            .build()
        return chain.proceed(newRequest)
    }
}
