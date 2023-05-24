package com.example.homework2.presentation.imagesCard

import android.provider.MediaStore.Images
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.homework2.data.model.Image
import com.example.homework2.data.model.Post
import com.example.homework2.data.model.Profile
import com.example.homework2.domain.DeleteImageUseCase
import com.example.homework2.domain.GetImageUseCase
import com.example.homework2.domain.GetImagesUseCase
import com.example.homework2.domain.GetProfilePostsUseCase
import com.example.homework2.domain.GetProfileUseCase
import com.example.homework2.domain.SubscribeUseCase
import com.example.homework2.domain.UnsubscribeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val getImageUseCase: GetImageUseCase,
    private val deleteImageUseCase: DeleteImageUseCase
) : ViewModel() {
    private val _imagesLiveData = MutableLiveData<PagingData<Image>>()
    val imagesLiveData: LiveData<PagingData<Image>> = _imagesLiveData
    private val _imageLiveData = MutableLiveData<Image>()
    val imageLiveData: LiveData<Image> = _imageLiveData

    fun getImage(imageId : String){
        viewModelScope.launch {
            getImageUseCase.execute(imageId).also {
                _imageLiveData.postValue(it)
            }
        }
    }
    fun deleteImage(imageId: String){
        viewModelScope.launch {
            deleteImageUseCase.execute(imageId)
        }
    }
    fun getImages(username: String){
        viewModelScope.launch {
            getImagesUseCase.execute(username).cachedIn(this).also { flow ->
                flow.collect{
                    _imagesLiveData.postValue(it)
                }
            }
        }
    }
}