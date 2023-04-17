package com.example.homework2.imagesCard

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class DataImages(
    val link: MutableList<String>,
    val id: String = UUID.randomUUID().toString(),
) : Parcelable