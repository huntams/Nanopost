package com.example.homework2.domain

import com.example.homework2.data.model.Profile
import com.example.homework2.data.remote.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository,
) {

    suspend fun execute(ProfileId : String) :Profile{
        return repository.getProfile(profileId = ProfileId)
    }
}