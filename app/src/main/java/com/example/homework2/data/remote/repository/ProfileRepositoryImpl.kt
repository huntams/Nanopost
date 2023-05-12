package com.example.homework2.data.remote.repository

import com.example.homework2.data.PrefsStorage
import com.example.homework2.data.mappers.ProfileMapper
import com.example.homework2.data.model.Profile
import com.example.homework2.data.remote.NanoPostUsernameApiService
import com.example.homework2.data.remote.NanopostApiService
import com.example.homework2.data.remote.NanopostAuthApiService
import com.example.homework2.data.remote.model.ApiResult
import com.example.homework2.data.remote.model.ApiToken
import com.example.homework2.data.remote.model.RegistrationRequest
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: NanopostApiService,
    private val usernameApiService: NanoPostUsernameApiService,
    private val profileMapper: ProfileMapper,
    private val authApiService: NanopostAuthApiService,
) : ProfileRepository {

    override suspend fun getToken(registrationRequest: RegistrationRequest): ApiToken {

        return authApiService.register(registrationRequest)
    }

    override suspend fun getProfile(profileId: String): Profile {
        return profileMapper.apiToModel(apiService.getProfile(profileId))
    }

    override suspend fun checkUsername(username : String): ApiResult {
        return usernameApiService.checkUsername(username)
    }
}