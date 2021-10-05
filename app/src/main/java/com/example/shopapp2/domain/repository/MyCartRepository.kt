package com.example.shopapp2.domain.repository

import com.example.shopapp2.domain.models.CartProduct

interface MyCartRepository {

    suspend fun addCartProduct(
        userId: String,
        cartProduct: CartProduct,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    )

    fun deleteCartProduct(
        userId: String,
        cartProduct: CartProduct,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    )

}