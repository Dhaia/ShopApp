package com.example.shopapp2.domain.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Product(

    var id: String = "",
    val title: String = "",
    val price: Int = 0,
    var imagesUrls: List<String?> = listOf(),
    val description: String = "",

    val sizes: List<String?> = listOf(),
    val colors: List<String?> = listOf(),
    val discount: Int = 0,
    val discountBoolean: Boolean = false,
    val type: String = "",

    @Contextual
    @ServerTimestamp var lastModified: Timestamp? = null,

    ) : Parcelable
