package com.example.homework2.data.remote.repository

import com.example.homework2.data.model.Post
import com.example.homework2.data.model.Profile
import com.example.homework2.data.remote.model.ApiPost

interface ProfileRepository {


    suspend fun getProfile(profileId: String): Profile
    suspend fun createPost(text: String?, list: List<ByteArray>?): Post
}