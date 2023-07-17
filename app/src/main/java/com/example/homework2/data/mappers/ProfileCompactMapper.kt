package com.example.homework2.data.mappers

import com.example.homework2.data.model.Profile
import com.example.homework2.data.model.ProfileCompact
import com.example.homework2.data.remote.model.ApiProfile
import com.example.homework2.data.remote.model.ApiProfileCompact
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