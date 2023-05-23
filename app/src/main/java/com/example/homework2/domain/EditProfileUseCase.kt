package com.example.homework2.domain

import com.example.homework2.data.model.Post
import com.example.homework2.data.remote.model.ApiResultResponse
import com.example.homework2.data.remote.repository.ProfileRepository
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend fun execute(
        profileId: String,
        displayName: String?,
        bio: String?,
        avatar: ByteArray?
    ): ApiResultResponse {
        return repository.editProfile(
            profileId = profileId,
            displayName = displayName,
            bio = bio,
            avatar = avatar
        )

    }
}