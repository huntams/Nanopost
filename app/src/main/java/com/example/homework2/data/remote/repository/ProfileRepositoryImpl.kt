package com.example.homework2.data.remote.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.homework2.data.PrefsStorage
import com.example.homework2.data.mappers.ImageMapper
import com.example.homework2.data.mappers.PostMapper
import com.example.homework2.data.mappers.ProfileCompactMapper
import com.example.homework2.data.mappers.ProfileMapper
import com.example.homework2.data.model.Image
import com.example.homework2.data.model.Post
import com.example.homework2.data.model.Profile
import com.example.homework2.data.model.ProfileCompact
import com.example.homework2.data.pagging.FeedPagingSource
import com.example.homework2.data.pagging.ImagePagingSource
import com.example.homework2.data.pagging.PostPagingSource
import com.example.homework2.data.pagging.ProfileCompactPagingSource
import com.example.homework2.data.remote.NanopostApiService
import com.example.homework2.data.remote.NanopostAuthApiService
import com.example.homework2.data.remote.model.ApiImage
import com.example.homework2.data.remote.model.ApiResult
import com.example.homework2.data.remote.model.ApiResultResponse
import com.example.homework2.data.remote.model.ApiToken
import com.example.homework2.data.remote.model.RegistrationRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Query
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: NanopostApiService,
    private val profileMapper: ProfileMapper,
    private val postMapper: PostMapper,
    private val imageMapper: ImageMapper,
    private val profileCompactMapper: ProfileCompactMapper,
    private val authApiService: NanopostAuthApiService,
    private val prefStorage: PrefsStorage
) : ProfileRepository {



    override suspend fun getToken(username: String, password: String): ApiToken {
        return authApiService.login(username, password)
    }

    override suspend fun getToken(registrationRequest: RegistrationRequest): ApiToken {
        return authApiService.register(registrationRequest)
    }

    override suspend fun subscribe(username: String): ApiResultResponse {
        return apiService.subscribe(username)
    }

    override suspend fun unsubscribe(username: String): ApiResultResponse {
        return apiService.unsubscribe(username)
    }

    override suspend fun searchProfile(query: String): Flow<PagingData<ProfileCompact>> {
        return Pager(
            config = PagingConfig(30, enablePlaceholders = false),
            pagingSourceFactory = { ProfileCompactPagingSource(apiService, query) },
        ).flow.map { pagingdata ->
            pagingdata.map {
                profileCompactMapper.apiToModel(it)
            }

        }
    }

    override suspend fun getImages(username: String): Flow<PagingData<Image>>{
        return Pager(
            config = PagingConfig(30, enablePlaceholders = false),
            pagingSourceFactory = { ImagePagingSource(apiService, username) },
        ).flow.map { pagingdata ->
            pagingdata.map {
                imageMapper.apiToModel(it)
            }

        }
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

    override suspend fun editProfile(
        profileId: String,
        displayName: String?,
        bio: String?,
        avatar: ByteArray?
    ): ApiResultResponse {
        var image: MultipartBody.Part? = null
        avatar?.let {
            image = MultipartBody.Part.createFormData(
                "avatar",
                "avatar.jpg",
                it.toRequestBody("image/*".toMediaType())
            )
        }
        return apiService.editProfile(
            profileId = "huntams",
            displayName = displayName?.toRequestBody(),
            bio = bio?.toRequestBody(),
            avatar = image
        )
    }
    override suspend fun getPost(postId: String): Post {
        return postMapper.apiToModel(apiService.getPost(postId))
    }

    override suspend fun getImage(imageId: String): Image {
        return imageMapper.apiToModel(apiService.getImage(imageId = imageId))
    }

    override suspend fun deleteImage(imageId: String): ApiResultResponse {
        return apiService.deleteImage(imageId = imageId)
    }



    override suspend fun getFeed(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(30, enablePlaceholders = false),
            pagingSourceFactory = { FeedPagingSource(apiService) },
        ).flow.map { pagingdata ->
            pagingdata.map {
                postMapper.apiToModel(it)
            }

        }
    }

    override suspend fun getProfilePosts(username: String): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(30, enablePlaceholders = false),
            pagingSourceFactory = { PostPagingSource(apiService, username) },
        ).flow.map { pagingdata ->
            pagingdata.map {
                postMapper.apiToModel(it)
            }

        }
    }

}