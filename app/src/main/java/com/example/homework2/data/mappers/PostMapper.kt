package com.example.homework2.data.mappers

import com.example.homework2.data.model.Post
import com.example.homework2.data.model.Profile
import com.example.homework2.data.remote.model.ApiPost
import com.example.homework2.data.remote.model.ApiProfile
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostMapper @Inject constructor() {

    fun apiToModel(apiPost: ApiPost) = Post(
        id = apiPost.id,
        owner= apiPost.owner,
        text = apiPost.text,
        dateCreated = apiPost.dateCreated,
        images = apiPost.images,
        likes = apiPost.likes

    )
}