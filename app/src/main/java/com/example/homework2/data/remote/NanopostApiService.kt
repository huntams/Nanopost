package com.example.homework2.data.remote

import com.example.homework2.data.model.Profile
import com.example.homework2.data.remote.model.ApiPost
import com.example.homework2.data.remote.model.ApiProfile
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface NanopostApiService {


    @GET("api/v1/profile/{profileId}")
    suspend fun getProfile(
        @Path("profileId") profileId: String,

    ) : ApiProfile
    @PUT("/api/v1/post")
    @Multipart
    suspend fun createPost(
        @Part("text") text: RequestBody?,
        @Part image1: MultipartBody.Part?,
        @Part image2: MultipartBody.Part?,
        @Part image3: MultipartBody.Part?,
        @Part image4: MultipartBody.Part?,
    ): ApiPost
}