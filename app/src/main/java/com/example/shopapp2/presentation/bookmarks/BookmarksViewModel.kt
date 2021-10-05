package com.example.shopapp2.presentation.bookmarks

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.domain.use_case.AddBookmarkUseCase
import com.example.shopapp2.presentation.UserViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel
@Inject
constructor(
    private val application: Application,
    private val firebaseAuth: FirebaseAuth,
    private val addBookmarkUseCase: AddBookmarkUseCase
) : ViewModel() {

    fun addBookmark(
        product: Product,
        userViewModel: UserViewModel
    ) {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val userid = currentUser.uid

            product.lastModified = Timestamp.now()

            // Check if cart items are less than 10
            if (userViewModel.user.value.cartProducts.size == 10) {
                Toast.makeText(
                    application.applicationContext,
                    "You can't have more than 10 items in your cart",
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                viewModelScope.launch {
                    addBookmarkUseCase.addBookmark(product, userid)
                }
            }
        } else {
            Toast.makeText(application.applicationContext, "User not logged in", Toast.LENGTH_LONG)
                .show()
        }
    }

    fun deleteBookmark(
        product: Product,
    ) {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val userid = currentUser.uid

            viewModelScope.launch {
                addBookmarkUseCase.deleteBookmark(product, userid)
            }
        } else {
            Toast.makeText(application.applicationContext, "User not logged in", Toast.LENGTH_LONG)
                .show()
        }
    }
}