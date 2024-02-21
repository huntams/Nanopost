package com.example.homework2.domain

import com.example.homework2.domain.repository.ProfileRepository
import javax.inject.Inject

class CheckUsernameUseCase @Inject constructor(

    private val repository: ProfileRepository
) {

    suspend fun execute(username: String): String {
        return repository.checkUsername(username)
    }
}