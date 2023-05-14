package com.example.homework2.data.remote

import com.example.homework2.data.remote.model.ApiToken
import com.example.homework2.data.remote.model.RegistrationRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NanopostAuthApiService {

    @POST("api/auth/register")
    suspend fun register(
        @Body registrationRequest: RegistrationRequest
    ) : ApiToken

    @GET("/api/auth/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    ) : ApiToken
}