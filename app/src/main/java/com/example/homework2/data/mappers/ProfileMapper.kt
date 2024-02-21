package com.example.homework2.data.mappers

import com.example.homework2.data.remote.model.ApiProfile
import com.example.homework2.domain.model.Profile
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileMapper @Inject constructor(
    private val imageMapper: ImageMapper
) {

    fun apiToModel(apiProfile: ApiProfile) = Profile(
        id = apiProfile.id,
        username = apiProfile.username,
        displayName = apiProfile.displayName,
        bio = apiProfile.bio,
        avatarId = apiProfile.avatarId,
        avatarSmall = apiProfile.avatarSmall,
        avatarLarge = apiProfile.avatarLarge,
        subscribed = apiProfile.subscribed,
        subscribersCount = apiProfile.subscribersCount,
        postsCount = apiProfile.postsCount,
        imagesCount = apiProfile.imagesCount,
        images = apiProfile.images.map {
            imageMapper.apiToModel(it)
        }

    )
}