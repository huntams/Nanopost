package com.example.homework2.data.mappers

import com.example.homework2.data.remote.model.ApiImage
import com.example.homework2.domain.model.Image
import com.example.homework2.domain.model.ImageSize
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageMapper @Inject constructor() {

    fun apiToModel(apiImage: ApiImage) = Image(
        id = apiImage.id,
        owner = apiImage.owner,
        sizes = apiImage.sizes.map {
            ImageSize(
                width = it.width,
                height = it.height,
                url = it.url
            )
        },
        dateCreated = apiImage.dateCreated,

        )
}