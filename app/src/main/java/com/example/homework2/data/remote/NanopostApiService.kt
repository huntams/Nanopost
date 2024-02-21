package com.example.homework2.data.remote


import com.example.homework2.data.remote.model.ApiImage
import com.example.homework2.data.remote.model.ApiPost
import com.example.homework2.data.remote.model.ApiProfile
import com.example.homework2.data.remote.model.ApiProfileCompact
import com.example.homework2.data.remote.model.ApiResultResponse
import com.example.homework2.data.remote.model.PageDataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface NanopostApiService {

    @GET("/api/v1/post/{postId}")
    suspend fun getPost(
        @Path("postId") postId: String,
    ): ApiPost

    @GET("/api/v1/images/{profileId}")
    suspend fun getImages(
        @Path("profileId") profileId: String,
        @Query("count") count: Int,
        @Query("Offset") offset: String?,
    ): PageDataResponse<ApiImage>

    @PUT("/api/v1/profile/{profileId}/subscribe")
    suspend fun subscribe(
        @Path("profileId") profileId: String,
    ): ApiResultResponse

    @DELETE("/api/v1/profile/{profileId}/subscribe")
    suspend fun unsubscribe(
        @Path("profileId") profileId: String,
    ): ApiResultResponse

    @GET("/api/v1/feed")
    suspend fun getFeed(
        @Query("count") count: Int,
        @Query("Offset") offset: String?,
    ): PageDataResponse<ApiPost>

    @GET("/api/v1/posts/{profileId}")
    suspend fun getProfilePosts(
        @Path("profileId") profileId: String,
        @Query("count") count: Int,
        @Query("Offset") offset: String?,
    ): PageDataResponse<ApiPost>

    @GET("api/v1/profile/{profileId}")
    suspend fun getProfile(
        @Path("profileId") profileId: String,
    ): ApiProfile

    @PUT("/api/v1/post")
    @Multipart
    suspend fun createPost(
        @Part("text") text: RequestBody?,
        @Part image1: MultipartBody.Part?,
        @Part image2: MultipartBody.Part?,
        @Part image3: MultipartBody.Part?,
        @Part image4: MultipartBody.Part?,
    ): ApiPost

    @GET("/api/v1/profile/search")
    suspend fun searchProfiles(
        @Query("query") query: String,
        @Query("count") count: Int,
        @Query("Offset") offset: String?,
    ): PageDataResponse<ApiProfileCompact>

    @PATCH("/api/v1/profile/{profileId}")
    @Multipart
    suspend fun editProfile(
        @Path("profileId") profileId: String,
        @Part("displayName") displayName: RequestBody?,
        @Part("bio") bio: RequestBody?,
        @Part avatar: MultipartBody.Part?,
    ): ApiResultResponse

    @GET("/api/v1/image/{imageId}")
    suspend fun getImage(
        @Path("imageId") imageId: String
    ): ApiImage

    @DELETE("/api/v1/image/{imageId}")
    suspend fun deleteImage(
        @Path("imageId") imageId: String
    ): ApiResultResponse

}