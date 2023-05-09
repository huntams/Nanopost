package com.example.homework2.data.remote

import com.example.homework2.data.model.Profile
import com.example.homework2.data.remote.model.ApiProfile
import retrofit2.http.GET
import retrofit2.http.Path

interface NanopostApiService {


    @GET("api/v1/profile/{profileId}")
    suspend fun getProfile(
        @Path("profileId") profileId: String,

    ) : ApiProfile
}