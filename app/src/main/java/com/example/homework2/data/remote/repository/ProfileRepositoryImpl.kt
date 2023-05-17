package com.example.homework2.data.remote.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.homework2.data.PrefsStorage
import com.example.homework2.data.mappers.PostMapper
import com.example.homework2.data.mappers.ProfileMapper
import com.example.homework2.data.model.Post
import com.example.homework2.data.model.Profile
import com.example.homework2.data.pagging.PostPagingSource
import com.example.homework2.data.remote.NanopostApiService
import com.example.homework2.data.remote.NanopostAuthApiService
import com.example.homework2.data.remote.model.ApiResult
import com.example.homework2.data.remote.model.ApiToken
import com.example.homework2.data.remote.model.RegistrationRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: NanopostApiService,
    private val profileMapper: ProfileMapper,
    private val postMapper: PostMapper,
    private val authApiService: NanopostAuthApiService,
    private val prefStorage: PrefsStorage
) : ProfileRepository {


    override suspend fun getToken(username: String, password: String): ApiToken {
        return authApiService.login(username, password)
    }

    override suspend fun getToken(registrationRequest: RegistrationRequest): ApiToken {
        return authApiService.register(registrationRequest)
    }

    override suspend fun getProfile(profileId: String): Profile {
        return profileMapper.apiToModel(apiService.getProfile(profileId))
    }

    override suspend fun checkUsername(username: String): ApiResult {
        return authApiService.checkUsername(username)
    }

    override suspend fun createPost(text: String?, list: List<ByteArray>?): Post {
        var image0: MultipartBody.Part? = null

        list?.getOrNull(0)?.let {
            image0 = MultipartBody.Part.createFormData(
                "image1",
                "image1.jpg",
                it.toRequestBody("image/*".toMediaType())
            )
        }
        var image1: MultipartBody.Part? = null
        list?.getOrNull(1)?.let {
            image1 = MultipartBody.Part.createFormData(
                "image2",
                "image2.jpg",
                it.toRequestBody("image/*".toMediaType())
            )
        }
        var image2: MultipartBody.Part? = null
        list?.getOrNull(2)?.let {
            image2 = MultipartBody.Part.createFormData(
                "image3",
                "image3.jpg",
                it.toRequestBody("image/*".toMediaType())
            )
        }
        var image3: MultipartBody.Part? = null
        list?.getOrNull(3)?.let {
            image3 = MultipartBody.Part.createFormData(
                "image4",
                "image4.jpg",
                it.toRequestBody("image/*".toMediaType())
            )
        }
        Log.i("$text", "${prefStorage.token}")
        return postMapper.apiToModel(
            apiService.createPost(
                text?.toRequestBody(),
                image0,
                image1,
                image2,
                image3
            )
        )
    }


    override suspend fun getProfilePosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(30, enablePlaceholders = false),
            pagingSourceFactory = { PostPagingSource(apiService) },
        ).flow.map { pagingdata ->
            pagingdata.map {
                postMapper.apiToModel(it)
            }

        }
    }

}