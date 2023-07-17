package com.example.homework2.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.homework2.data.PrefsStorage
import com.example.homework2.data.remote.NanopostApiService
import com.example.homework2.data.remote.NanopostAuthApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    private const val BASE_URL = "https://nanopost.evolitist.com/"

    //Создаёт метку для типа
    @Qualifier
    annotation class AuthClient


    @Provides
    @Singleton
    @AuthClient
    fun provideAuthRetrofit(
        json: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient: OkHttpClient,
        json: Converter.Factory,
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(json)
            .build()
    }

    @Provides
    @Singleton
    fun provideJsonConverter(): Converter.Factory {
        return Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun provideNanopostApiService(retrofit: Retrofit): NanopostApiService {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideNanopostAuthApiService(
        @AuthClient retrofit: Retrofit,
    ): NanopostAuthApiService {
        return retrofit.create()
    }


    @Provides
    @Singleton
    fun provideAuthInterceptor(
        prefsStorage: PrefsStorage
    ): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()


            request.addHeader(
                "Authorization",
                "Bearer ${prefsStorage.token}"
            )
            chain.proceed(request.build())
        }
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        authInterceptor: Interceptor,
        @ApplicationContext context: Context,
    ): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(
            ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        ).addInterceptor(authInterceptor).build()
    }


}