package com.example.homework2.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.example.homework2.data.remote.model.ApiPost
import com.example.homework2.domain.GetProfilePostsUseCase
import com.example.homework2.domain.GetProfileUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getProfilePostsUseCase: GetProfilePostsUseCase
) : ViewModel() {

    private val _postLiveData = MutableLiveData< PagingData<ApiPost>>()
    val postLiveData : LiveData<PagingData<ApiPost>> = _postLiveData

    fun getProfile(){
        viewModelScope.launch {
            getProfilePostsUseCase.execute().collect{data ->
                _postLiveData.value = data.map {
                    it.copy(
                        text = it.text
                    )
                }

            }
        }
    }
}