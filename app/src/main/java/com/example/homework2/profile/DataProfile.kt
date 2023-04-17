package com.example.homework2.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class DataProfile(
    val name: String,
    val title: String,
    val date: String,
    val link: String,
    val id: String = UUID.randomUUID().toString(),
) : Parcelable
