package com.example.homework2.domain


import com.example.homework2.domain.repository.ProfileRepository
import javax.inject.Inject

class SubscribeUseCase @Inject constructor(

    private val repository: ProfileRepository
) {

    suspend fun execute(username: String) : Boolean {
        return repository.subscribe(username)
    }
}