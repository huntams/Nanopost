package com.example.homework2.data.mappers

import com.example.homework2.data.remote.model.ApiPost
import com.example.homework2.domain.model.Like
import com.example.homework2.domain.model.Post
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostMapper @Inject constructor(
    private val profileCompactMapper: ProfileCompactMapper,
    private val imageMapper: ImageMapper
) {

    fun apiToModel(apiPost: ApiPost) = Post(
        id = apiPost.id,
        owner = profileCompactMapper.apiToModel(apiPost.owner),
        text = apiPost.text,
        dateCreated = apiPost.dateCreated,
        images = apiPost.images.map {
            imageMapper.apiToModel(it)
        },
        likes = Like(
            liked = apiPost.likes.liked,
            likesCount = apiPost.likes.likesCount
        )

    )
}