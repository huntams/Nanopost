package com.example.homework2.presentation.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.homework2.domain.model.Post
import com.example.homework2.domain.model.Profile
import com.example.homework2.domain.model.ProfileCompact
import com.example.homework2.domain.GetFeedUseCase
import com.example.homework2.domain.SearchProfileCompactUseCase
import com.example.homework2.domain.SubscribeUseCase
import com.example.homework2.domain.UnsubscribeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getFeedUseCase: GetFeedUseCase,
    private val searchProfileCompactUseCase: SearchProfileCompactUseCase,
    private val subscribeUseCase: SubscribeUseCase,
    private val unsubscribeUseCase: UnsubscribeUseCase,
): ViewModel() {
    private val _postsLiveData = MutableLiveData<PagingData<Post>>()
    val postsLiveData: LiveData<PagingData<Post>> = _postsLiveData
    private val _profileLiveData = MutableLiveData<PagingData<ProfileCompact>>()
    val profileLiveData: LiveData<PagingData<ProfileCompact>> = _profileLiveData
    private val _usernameLiveData = MutableLiveData<Boolean>()
    val usernameLiveData: LiveData<Boolean> = _usernameLiveData

    fun searchProfiles(query: String){
        viewModelScope.launch {
            searchProfileCompactUseCase.execute(query).cachedIn(this).also { flow ->
                flow.collect{
                    _profileLiveData.postValue(it)
                }
            }
        }
    }

    fun getFeed(){
        viewModelScope.launch {
            getFeedUseCase.execute().cachedIn(this).also { flow ->
                flow.collect {
                    _postsLiveData.postValue(it)
                }
            }
        }
    }
    fun subscribe(username: String) {
        viewModelScope.launch {
            subscribeUseCase.execute(username)
                .also {
                    _usernameLiveData.postValue(it)
                }
        }
    }

    fun unsubscribe(username: String) {
        viewModelScope.launch {
            unsubscribeUseCase.execute(username)
                .also {
                    _usernameLiveData.postValue(it)
                }
        }
    }
}
