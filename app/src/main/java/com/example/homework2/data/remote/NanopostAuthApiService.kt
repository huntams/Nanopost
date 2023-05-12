package com.example.homework2.data.remote

import com.example.homework2.data.remote.model.ApiToken
import com.example.homework2.data.remote.model.RegistrationRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface NanopostAuthApiService {

    @POST("api/auth/register")
    suspend fun register(
        @Body registrationRequest: RegistrationRequest
    ) : ApiToken
}