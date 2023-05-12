package com.example.homework2.data.remote.repository

import com.example.homework2.data.model.Profile
import com.example.homework2.data.remote.model.ApiResult
import com.example.homework2.data.remote.model.ApiToken
import com.example.homework2.data.remote.model.RegistrationRequest

interface ProfileRepository {


    suspend fun getToken(registrationRequest: RegistrationRequest) : ApiToken
    suspend fun checkUsername(username : String) : ApiResult
    suspend fun getProfile(profileId: String) : Profile
}