package com.example.homework2.domain

import com.example.homework2.domain.repository.ProfileRepository
import javax.inject.Inject

class UnsubscribeUseCase @Inject constructor(

    private val repository: ProfileRepository
) {

    suspend fun execute(username: String) : Boolean {
        return repository.unsubscribe(username)
    }
}