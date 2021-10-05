package com.example.shopapp2.domain.models


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartProduct(

    var productId: String = "",
    val productTitle: String = "",
    val productPrice: Int = 0,
    var productImagesUrls: List<String?> = listOf(),
    val productDescription: String = "",
    val productSizes: List<String?> = listOf(),
    val productColors: List<String?> = listOf(),
    val productDiscount: Int = 0,

    var size: String = "",
    var color: String = "",

    ) : Parcelable