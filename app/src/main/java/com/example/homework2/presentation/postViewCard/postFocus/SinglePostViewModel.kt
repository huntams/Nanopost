package com.example.homework2.presentation.postViewCard.postFocus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework2.domain.GetPostUseCase
import com.example.homework2.domain.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SinglePostViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase,
) : ViewModel() {

    private val _postLiveData = MutableLiveData<Post>()
    val postLiveData: LiveData<Post> = _postLiveData

    fun getPost(postId: String) {
        viewModelScope.launch {
            getPostUseCase.execute(postId).also {
                _postLiveData.postValue(it)
            }
        }

    }
}
