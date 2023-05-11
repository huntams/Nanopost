package com.example.homework2.di

import com.example.homework2.data.remote.NanopostApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    private const val BASE_URL = "https://nanopost.evolitist.com/"


    @Provides
    @Singleton
    fun provideRetrofit(json: Converter.Factory,): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json)
            .build()
    }
    @Provides
    @Singleton
    fun provideJsonConverter() : Converter.Factory{
        return Json {
            ignoreUnknownKeys= true }.asConverterFactory("application/json".toMediaType())
    }
    @Provides
    @Singleton
    fun provideNanopostApiService(retrofit: Retrofit): NanopostApiService {
        return retrofit.create()
    }


}