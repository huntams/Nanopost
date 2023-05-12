package com.example.homework2.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface NanopostPasswordApiService {

    @GET("/api/auth/login")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    )
}