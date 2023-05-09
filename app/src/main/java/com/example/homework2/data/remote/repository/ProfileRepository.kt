package com.example.homework2.data.remote.repository

import com.example.homework2.data.model.Profile

interface ProfileRepository {


    suspend fun getProfile(profileId: String) : Profile
}