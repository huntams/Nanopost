package com.example.homework2.data.remote

import com.example.homework2.data.remote.model.ApiPost
import com.example.homework2.data.remote.model.ApiProfile
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NanopostApiService {


    @GET("/api/v1/post/{postId}")
    suspend fun getPost(
        @Path("postId") postId: String,
    )

    @GET("/api/v1/posts/{profileId}")
    suspend fun getProfilePosts(
        @Path("profileId") profileId : String,
        @Query("count") count : Int,
        @Query("Offset") offset : String?,
    ) : ApiPost
    @GET("api/v1/profile/{profileId}")
    suspend fun getProfile(
        @Path("profileId") profileId: String,

        ): ApiProfile
}