package com.example.homework2.data.remote.repository

import androidx.paging.PagingData
import com.example.homework2.data.model.Post
import com.example.homework2.data.model.Profile
import com.example.homework2.data.remote.model.ApiResult
import com.example.homework2.data.remote.model.ApiToken
import com.example.homework2.data.remote.model.RegistrationRequest
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun createPost(text: String?, list: List<ByteArray>?): Post
    suspend fun getToken(username: String, password: String): ApiToken
    suspend fun getToken(registrationRequest: RegistrationRequest): ApiToken
    suspend fun checkUsername(username: String): ApiResult
    suspend fun getProfile(profileId: String): Profile
    suspend fun getProfilePosts(): Flow<PagingData<Post>>
}