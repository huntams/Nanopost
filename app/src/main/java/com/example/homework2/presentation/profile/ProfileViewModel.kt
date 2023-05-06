package com.example.homework2.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework2.base.BaseViewModel
import com.example.homework2.data.model.Profile
import com.example.homework2.data.remote.ResultLoader
import com.example.homework2.domain.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
) : BaseViewModel() {
    private val _profileLiveData = MutableLiveData<ResultLoader<Profile>>()
    val profileLiveData: LiveData<ResultLoader<Profile>> = _profileLiveData
    fun getProfile(profileId: String = "evo") {
        /*
        viewModelScope.launch {
            getProfileUseCase.execute(profileId).also { profile ->
                _profileLiveData.postValue(profile)
            }
        }

         */
        _profileLiveData.loadData{
            getProfileUseCase.execute(profileId)
        }
    }
}