package com.example.homework2.domain

import com.example.homework2.domain.repository.ProfileRepository
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend fun execute(
        profileId: String,
        displayName: String?,
        bio: String?,
        avatar: ByteArray?
    ): Boolean {
        return repository.editProfile(
            profileId = profileId,
            displayName = displayName,
            bio = bio,
            avatar = avatar
        )

    }
}