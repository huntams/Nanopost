package com.example.homework2.domain

import com.example.homework2.data.remote.model.ApiToken
import com.example.homework2.data.remote.model.RegistrationRequest
import com.example.homework2.data.remote.repository.ProfileRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend fun execute(registrationRequest: RegistrationRequest) : ApiToken{
        return repository.getToken(registrationRequest)
    }
}