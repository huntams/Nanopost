package com.example.homework2.data.remote.repository

import com.example.homework2.data.mappers.ProfileMapper
import com.example.homework2.data.model.Profile
import com.example.homework2.data.remote.NanopostApiService
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: NanopostApiService,
    private val profileMapper: ProfileMapper,
) : ProfileRepository {

    override suspend fun getProfile(profileId: String): Profile {
        return profileMapper.apiToModel(apiService.getProfile(profileId))
    }
}