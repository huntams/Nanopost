package com.example.homework2.presentation.profile

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
import com.example.homework2.domain.EditProfileUseCase
import com.example.homework2.domain.GetProfilePostsUseCase
import com.example.homework2.domain.GetProfileUseCase
import com.example.homework2.domain.GetTokenUseCase
import com.example.homework2.domain.SubscribeUseCase
import com.example.homework2.domain.UnsubscribeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getProfilePostsUseCase: GetProfilePostsUseCase,
    private val subscribeUseCase: SubscribeUseCase,
    private val unsubscribeUseCase: UnsubscribeUseCase,
    private val editProfileUseCase: EditProfileUseCase
) : ViewModel() {
    private val _profileLiveData = MutableLiveData<Profile>()
    val profileLiveData: LiveData<Profile> = _profileLiveData
    private val _postsLiveData = MutableLiveData<PagingData<Post>>()
    val postsLiveData: LiveData<PagingData<Post>> = _postsLiveData
    private val _postLiveData = MutableLiveData<Post>()
    val postLiveData: LiveData<Post> = _postLiveData
    private val _usernameLiveData = MutableLiveData<Boolean>()
    val usernameLiveData: LiveData<Boolean> = _usernameLiveData
    fun getProfile(profileId: String = "evo") {
        viewModelScope.launch {
            getProfileUseCase.execute(profileId).also { profile ->
                _profileLiveData.postValue(profile)
            }
        }
    }

    fun setPost(post: Post) {
        _postLiveData.value = post

    }

    fun getProfilePosts(username: String) {
        viewModelScope.launch {
            getProfilePostsUseCase.execute(username).cachedIn(this).also { flow ->
                flow.collect {
                    _postsLiveData.postValue(it)
                }
            }
        }
    }
    fun editProfile(
        profileId: String,
        displayName: String?,
        bio: String?,
        avatar: ByteArray?
    ) {
        viewModelScope.launch {
            editProfileUseCase.execute(
                profileId = profileId,
                displayName = displayName,
                bio = bio,
                avatar = avatar
            ).also {
                _usernameLiveData.postValue(it.result)
            }
        }

    }


    fun subscribe(username: String) {
        viewModelScope.launch {
            subscribeUseCase.execute(username)
                .also {
                    _usernameLiveData.postValue(it.result)
                }
        }
    }

    fun unsubscribe(username: String) {
        viewModelScope.launch {
            unsubscribeUseCase.execute(username)
                .also {
                    _usernameLiveData.postValue(it.result)
                }
        }
    }
}