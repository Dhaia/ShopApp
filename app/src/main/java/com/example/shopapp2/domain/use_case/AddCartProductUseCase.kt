package com.example.shopapp2.domain.use_case

import android.app.Application
import android.widget.Toast
import com.example.shopapp2.domain.models.CartProduct
import com.example.shopapp2.domain.repository.MyCartRepository

class AddCartProductUseCase(
    private val cartRepository: MyCartRepository,
    private val application: Application,
) {

    fun deleteCartProduct(
        cartProduct: CartProduct,
        userId: String?,
    ) {
        fun onSuccess() {
            Toast.makeText(
                application.applicationContext,
                "Item deleted", Toast.LENGTH_LONG
            ).show()
        }

        fun onFailure(error: String?) {
            Toast.makeText(
                application.applicationContext,
                "Couldn't delete the item $error", Toast.LENGTH_LONG
            ).show()
        }

        if (userId != null) {
            cartRepository.deleteCartProduct(
                cartProduct = cartProduct,
                userId = userId,
                onSuccess = ::onSuccess,
                onFailure = ::onFailure
            )
        }
    }

    suspend fun addCartProduct(
        cartProduct: CartProduct,
        userId: String?,
    ) {
        fun onSuccess() {
            Toast.makeText(
                application.applicationContext,
                "Item added to your Cart", Toast.LENGTH_LONG
            ).show()
        }

        fun onFailure(error: String?) {
            Toast.makeText(
                application.applicationContext,
                "Couldn't add the item $error", Toast.LENGTH_LONG
            ).show()
        }

        if (userId != null) {
            cartRepository.addCartProduct(
                cartProduct = cartProduct,
                userId = userId,
                onSuccess = ::onSuccess,
                onFailure = ::onFailure
            )
        }
    }
}