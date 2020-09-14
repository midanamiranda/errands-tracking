package com.example.errandstracking.di

import com.example.errandstracking.data.remote.api.ErrandsTrackingService
import com.example.errandstracking.data.remote.api.ErrandsTrackingServiceHelper
import com.example.errandstracking.data.remote.api.ErrandsTrackingServiceHelperImpl
import com.example.errandstracking.data.remote.api.RetrofitBuilder
import com.example.errandstracking.data.remote.interceptors.MediaTypeInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideHttpClient() : OkHttpClient {

        val mediaTypeInterceptor = MediaTypeInterceptor()
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val builder = OkHttpClient.Builder()

        builder.interceptors()
            .addAll(listOf(mediaTypeInterceptor, loggingInterceptor))

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideAppRetrofit(client: OkHttpClient) : Retrofit
            = RetrofitBuilder.getRetrofit(null, client)

    @Provides
    @Singleton
    fun provideErrandsTrackingService(retrofit: Retrofit) : ErrandsTrackingService
            = retrofit.create(ErrandsTrackingService::class.java)

    @Provides
    @Singleton
    fun provideErrandsTrackingServiceHelper(errandsTrackingService: ErrandsTrackingService) : ErrandsTrackingServiceHelper
            = ErrandsTrackingServiceHelperImpl(errandsTrackingService)

}