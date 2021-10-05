package com.example.shopapp2.domain.use_case

import android.app.Application
import android.widget.Toast
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.domain.repository.BookmarksRepository

class AddBookmarkUseCase(
    private val bookmarksRepository: BookmarksRepository,
    private val application: Application,
) {

    suspend fun deleteBookmark(
        product: Product,
        userId: String?,
    ) {
        fun onSuccess() {
            Toast.makeText(
                application.applicationContext,
                "Item deleted from your bookmarks", Toast.LENGTH_LONG
            ).show()
        }

        fun onFailure(error: String?) {
            Toast.makeText(
                application.applicationContext,
                "Couldn't remove the item from your bookmarks $error", Toast.LENGTH_LONG
            ).show()
        }

        if (userId != null) {
            bookmarksRepository.deleteBookmark(
                product, userId, ::onSuccess, ::onFailure
            )
        }
    }

    suspend fun addBookmark(
        product: Product,
        userId: String?,
    ) {
        fun onSuccess() {
            Toast.makeText(
                application.applicationContext,
                "Item added to your bookmarks", Toast.LENGTH_LONG
            ).show()
        }

        fun onFailure(error: String?) {
            Toast.makeText(
                application.applicationContext,
                "Couldn't add the item $error", Toast.LENGTH_LONG
            ).show()
        }

        if (userId != null) {
            bookmarksRepository.addBookmark(
                product = product,
                userId, ::onSuccess, ::onFailure
            )
        }
    }
}