package com.example.shopapp2.data.repository

import com.example.shopapp2.common.FirebaseConstants
import com.example.shopapp2.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class UserRepositoryImpl(
    val firebaseFirestore: FirebaseFirestore,
) : UserRepository {

    override fun updateUserData(
        updatedDate: HashMap<String, Any>,
        userId: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        firebaseFirestore
            .collection(FirebaseConstants.USERS_COLLECTION)
            .document(userId)
            .update(updatedDate)
            .addOnSuccessListener {
                onSuccess()
                Timber.d("updateUserData successfully updated!")
            }
            .addOnFailureListener { exception ->
                onFailure(exception.toString())
                Timber.d("updateUserData Error $exception")
            }
    }

}