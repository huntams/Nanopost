package com.example.homework2.data.mappers

import com.example.homework2.data.remote.model.ApiProfileCompact
import com.example.homework2.domain.model.ProfileCompact
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileCompactMapper @Inject constructor() {

    fun apiToModel(apiProfile: ApiProfileCompact) = ProfileCompact(
        id = apiProfile.id,
        username = apiProfile.username,
        displayName = apiProfile.displayName,
        subscribed = apiProfile.subscribed,
        avatarUrl = apiProfile.avatarUrl,

    )
}