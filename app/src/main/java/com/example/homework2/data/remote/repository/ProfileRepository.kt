package com.example.homework2.data.remote.repository

import androidx.paging.PagingData
import com.example.homework2.data.model.Image
import com.example.homework2.data.model.Post
import com.example.homework2.data.model.Profile
import com.example.homework2.data.model.ProfileCompact
import com.example.homework2.data.remote.model.ApiImage
import com.example.homework2.data.remote.model.ApiResult
import com.example.homework2.data.remote.model.ApiResultResponse
import com.example.homework2.data.remote.model.ApiToken
import com.example.homework2.data.remote.model.RegistrationRequest
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun createPost(text: String?, list: List<ByteArray>?): Post

    suspend fun editProfile(
        profileId: String,
        displayName: String?,
        bio: String?,
        avatar: ByteArray?
    ): ApiResultResponse

    suspend fun getImage(imageId: String): Image

    suspend fun deleteImage(imageId: String) : ApiResultResponse
    suspend fun getPost(postId: String): Post
    suspend fun getToken(username: String, password: String): ApiToken
    suspend fun getToken(registrationRequest: RegistrationRequest): ApiToken
    suspend fun checkUsername(username: String): ApiResult
    suspend fun getProfile(profileId: String): Profile
    suspend fun getProfilePosts(username: String): Flow<PagingData<Post>>
    suspend fun getFeed(): Flow<PagingData<Post>>
    suspend fun searchProfile(query: String) : Flow<PagingData<ProfileCompact>>
    suspend fun subscribe(username: String): ApiResultResponse
    suspend fun unsubscribe(username: String): ApiResultResponse
    suspend fun getImages(username: String): Flow<PagingData<Image>>
}