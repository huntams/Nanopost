package com.example.homework2.data.mappers

import com.example.homework2.data.model.Image
import com.example.homework2.data.model.Post
import com.example.homework2.data.remote.model.ApiImage
import com.example.homework2.data.remote.model.ApiPost
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageMapper @Inject constructor() {

    fun apiToModel(apiImage: ApiImage) = Image(
        id = apiImage.id,
        owner = apiImage.owner,
        sizes = apiImage.sizes,
        dateCreated = apiImage.dateCreated,

        )
}