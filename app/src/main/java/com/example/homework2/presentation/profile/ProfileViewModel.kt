package com.example.homework2.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework2.data.model.Profile
import com.example.homework2.data.remote.model.RegistrationRequest
import com.example.homework2.domain.CheckUsernameUseCase
import com.example.homework2.domain.GetProfileUseCase
import com.example.homework2.domain.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val checkUsernameUseCase: CheckUsernameUseCase,
    private val getTokenUseCase: GetTokenUseCase
): ViewModel() {
    private val _profileLiveData = MutableLiveData<Profile>()
    val profileLiveData: LiveData<Profile> = _profileLiveData
    private val _usernameLiveData = MutableLiveData<String>()
    val usernameLiveData: LiveData<String> = _usernameLiveData

    fun getToken(registrationRequest: RegistrationRequest){
        viewModelScope.launch {
            _usernameLiveData.postValue(getTokenUseCase.execute(registrationRequest).token)
        }
    }
    fun getProfile(profileId: String="evo"){
        viewModelScope.launch {
            getProfileUseCase.execute(profileId).also { profile ->
                _profileLiveData.postValue(profile)
            }
        }
    }
    fun checkUsername(username : String) {
        viewModelScope.launch {
            checkUsernameUseCase.execute(username)
                .also {
                _usernameLiveData.postValue(it.result)
            }
        }
    }
}