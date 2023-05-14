package com.example.homework2.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework2.data.model.Profile
import com.example.homework2.data.remote.model.RegistrationRequest
import com.example.homework2.domain.CheckUsernameUseCase
import com.example.homework2.domain.GetProfileUseCase
import com.example.homework2.domain.GetTokenLoginUseCase
import com.example.homework2.domain.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val checkUsernameUseCase: CheckUsernameUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val getTokenLoginUseCase: GetTokenLoginUseCase,
) : ViewModel() {
    private val _usernameLiveData = MutableLiveData<String>()
    val usernameLiveData: LiveData<String> = _usernameLiveData

    fun getToken(username: String, password: String) {
        viewModelScope.launch {
            _usernameLiveData.postValue(getTokenLoginUseCase.execute(username,password).token)
        }
    }

    fun getToken(registrationRequest: RegistrationRequest) {
        viewModelScope.launch {
            _usernameLiveData.postValue(getTokenUseCase.execute(registrationRequest).token)
        }
    }

    fun checkUsername(username: String) {
        viewModelScope.launch {
            checkUsernameUseCase.execute(username)
                .also {
                    _usernameLiveData.postValue(it.result)
                }
        }
    }
}