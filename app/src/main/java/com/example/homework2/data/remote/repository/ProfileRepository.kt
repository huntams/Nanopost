package com.example.homework2.data.remote.repository

import androidx.paging.PagingData
import com.example.homework2.data.model.Profile
import com.example.homework2.data.remote.model.ApiPost
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {


    suspend fun getProfile(profileId: String) : Profile
    suspend fun getProfilePosts() : Flow<PagingData<ApiPost>>
}