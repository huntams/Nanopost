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
): ViewModel() {
    private val _profileLiveData = MutableLiveData<Profile>()
    val profileLiveData: LiveData<Profile> = _profileLiveData
    fun getProfile(profileId: String="evo"){
        viewModelScope.launch {
            getProfileUseCase.execute(profileId).also { profile ->
                _profileLiveData.postValue(profile)
            }
        }
    }
}