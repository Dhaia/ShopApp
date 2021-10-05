package com.example.shopapp2.data.repository

import com.example.shopapp2.common.FirebaseConstants
import com.example.shopapp2.domain.models.CartProduct
import com.example.shopapp2.domain.repository.MyCartRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class MyCartRepositoryImpl(
    val firebaseFirestore: FirebaseFirestore,
) : MyCartRepository {

    override suspend fun addCartProduct(
        userId: String,
        cartProduct: CartProduct,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    ) {

        firebaseFirestore
            .collection(FirebaseConstants.USERS_COLLECTION)
            .document(userId)
            .update("cartProducts", FieldValue.arrayUnion(cartProduct))
            .addOnSuccessListener {
                onSuccess()
                Timber.d("addCartProduct successfully updated!")
            }
            .addOnFailureListener { exception ->
                onFailure(exception.toString())
                Timber.d("addCartProduct Error updating document")
            }
    }

    override fun deleteCartProduct(
        userId: String,
        cartProduct: CartProduct,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        firebaseFirestore
            .collection(FirebaseConstants.USERS_COLLECTION)
            .document(userId)
            .update("cartProducts", FieldValue.arrayRemove(cartProduct))
            .addOnSuccessListener {
                onSuccess()
                Timber.d("deleteCartProduct successfully updated!")
            }
            .addOnFailureListener { exception ->
                onFailure(exception.toString())
                Timber.d("deleteCartProduct Error updating document")
            }
    }

}