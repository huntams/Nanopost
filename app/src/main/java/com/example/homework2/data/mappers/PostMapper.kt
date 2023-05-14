package com.example.homework2.data.mappers

import com.example.homework2.data.model.Post
import com.example.homework2.data.remote.model.ApiPost
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostMapper @Inject constructor() {


    fun apiToModel(apiPost: ApiPost) = Post(
        id = apiPost.id,
        text = apiPost.text,
    )

}