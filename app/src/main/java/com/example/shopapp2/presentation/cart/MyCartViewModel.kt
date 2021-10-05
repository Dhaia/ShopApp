package com.example.shopapp2.presentation.cart

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp2.domain.models.CartProduct
import com.example.shopapp2.domain.use_case.AddCartProductUseCase
import com.example.shopapp2.presentation.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCartViewModel
@Inject
constructor(
    private val application: Application,
    private val addCartProductUseCase: AddCartProductUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    fun addCartProduct(
        cartProduct: CartProduct,
        userViewModel: UserViewModel
    ) {

        val currentUser = firebaseAuth.currentUser

        // Check if the user is logged in
        if (currentUser != null) {
            val userid = currentUser.uid

            // Check if cart items are less than 10 in order to proceed
            if (userViewModel.user.value.cartProducts.size == 10) {
                Toast.makeText(
                    application.applicationContext,
                    "You can't have more than 10 items in your cart",
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                viewModelScope.launch {
                    addCartProductUseCase.addCartProduct(
                        cartProduct = cartProduct,
                        userId = userid,
                    )
                }
            }
        } else {
            Toast.makeText(
                application.applicationContext, "User not logged in",
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

    fun deleteCartProduct(cartProduct: CartProduct) {

        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val userid = currentUser.uid

            viewModelScope.launch {

                addCartProductUseCase.deleteCartProduct(
                    cartProduct = cartProduct,
                    userId = userid,
                )
            }
        } else {
            Toast.makeText(application.applicationContext, "User not logged in", Toast.LENGTH_LONG)
                .show()
        }
    }

}