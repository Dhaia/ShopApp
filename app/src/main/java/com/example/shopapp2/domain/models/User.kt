package com.example.shopapp2.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    var id: String = "",

    var email: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val state: String = "",
    val district: String = "",

    val cartProducts: List<CartProduct> = listOf(),
    val bookmarksProducts: List<Product> = listOf(),
    val oldOrders: List<Product> = listOf(),
    val activeOrders: List<Product> = listOf(),

    ) : Parcelable
