package com.example.homework2.presentation.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.homework2.data.model.Post
import com.example.homework2.data.model.Profile
import com.example.homework2.domain.GetFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getFeedUseCase: GetFeedUseCase
): ViewModel() {
    private val _postsLiveData = MutableLiveData<PagingData<Post>>()
    val postsLiveData: LiveData<PagingData<Post>> = _postsLiveData


    fun getFeed(){
        viewModelScope.launch {
            getFeedUseCase.execute().cachedIn(this).also { flow ->
                flow.collect {
                    _postsLiveData.postValue(it)
                }
            }
        }
    }
}
