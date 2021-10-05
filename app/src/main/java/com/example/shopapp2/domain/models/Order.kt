package com.example.shopapp2.domain.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(

    var productId: String = "",
    val productTitle: String = "",
    val productPrice: Int = 0,
    var productImagesUrls: List<String?> = listOf(),
    val productDescription: String = "",
    val productSizes: List<String?> = listOf(),
    val productColors: List<String?> = listOf(),
    val productDiscount: Int = 0,

    var orderSize: String = "",
    var orderColor: String = "",
    var orderQuantity: Int = 0,
    var orderPrice: Int = 0,

    var userId: String = "",
    val userName: String = "",
    val userEmail: String = "",
    val userPhoneNumber: String = "",
    val userState: String = "",
    val userDistrict: String = "",

    @ServerTimestamp
    val createdDate: Timestamp? = null,

    ) : Parcelable