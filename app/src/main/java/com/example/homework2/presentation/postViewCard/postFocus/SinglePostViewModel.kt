package com.example.homework2.presentation.postViewCard.postFocus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.homework2.data.model.Post
import com.example.homework2.data.model.Profile
import com.example.homework2.data.remote.model.RegistrationRequest
import com.example.homework2.domain.CheckUsernameUseCase
import com.example.homework2.domain.GetPostUseCase
import com.example.homework2.domain.GetProfilePostsUseCase
import com.example.homework2.domain.GetProfileUseCase
import com.example.homework2.domain.GetTokenUseCase
import com.example.homework2.domain.SubscribeUseCase
import com.example.homework2.domain.UnsubscribeUseCase
import com.example.homework2.presentation.postViewCard.AddPostData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine


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
