package com.example.shopapp2.data.repository

import com.example.shopapp2.common.FirebaseConstants
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.domain.repository.BookmarksRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class BookmarksRepositoryImpl (
    val firebaseFirestore: FirebaseFirestore
    ): BookmarksRepository{

    override suspend fun addBookmark(
        product: Product,
        userId: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        firebaseFirestore.collection(FirebaseConstants.USERS_COLLECTION)
            .document(userId)
            .update("bookmarksProducts", FieldValue.arrayUnion(product))
            .addOnSuccessListener {
                onSuccess()
                Timber.d("addBookmark successfully updated!") }
            .addOnFailureListener { exception ->
                Timber.d("addBookmark Error updating document $exception")
                onFailure(exception.toString())
            }
    }

    override suspend fun deleteBookmark(
        product: Product,
        userId: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {

        Timber.d("fgf ^$userId / $product")
        firebaseFirestore.collection(FirebaseConstants.USERS_COLLECTION)
            .document(userId)
            .update("bookmarksProducts", FieldValue.arrayRemove(product))
            .addOnSuccessListener {
                onSuccess()
                Timber.d("deleteBookmark successfully deleted!") }
            .addOnFailureListener { exception ->
                Timber.d("deleteBookmark Error")
                onFailure(exception.toString())
            }
    }
}