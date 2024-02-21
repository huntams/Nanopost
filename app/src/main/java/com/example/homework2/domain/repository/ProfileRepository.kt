package com.example.homework2.domain.repository

import androidx.paging.PagingData
import com.example.homework2.domain.model.Image
import com.example.homework2.domain.model.Post
import com.example.homework2.domain.model.Profile
import com.example.homework2.domain.model.ProfileCompact
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
    ): Boolean

    suspend fun getImage(imageId: String): Image

    suspend fun deleteImage(imageId: String) : Boolean
    suspend fun getPost(postId: String): Post
    suspend fun getToken(username: String, password: String): ApiToken
    suspend fun getToken(registrationRequest: RegistrationRequest): ApiToken
    suspend fun checkUsername(username: String): String
    suspend fun getProfile(profileId: String): Profile
    suspend fun getProfilePosts(username: String): Flow<PagingData<Post>>
    suspend fun getFeed(): Flow<PagingData<Post>>
    suspend fun searchProfile(query: String) : Flow<PagingData<ProfileCompact>>
    suspend fun subscribe(username: String): Boolean
    suspend fun unsubscribe(username: String): Boolean
    suspend fun getImages(username: String): Flow<PagingData<Image>>
}