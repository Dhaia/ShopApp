package com.example.shopapp2.data.repository

import com.example.shopapp2.common.FirebaseConstants
import com.example.shopapp2.domain.models.User
import com.example.shopapp2.domain.repository.LoginRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class LoginRepositoryImpl(
    val firestoreDb: FirebaseFirestore,
    val firebaseAuth: FirebaseAuth,
) : LoginRepository {

    override fun loginUserWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: (String?) -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = task.result?.user
                    onSuccess(firebaseUser?.uid)
                } else {
                    val error = task.exception?.message.toString()
                    onFailure(error)
                }
            }
    }

    override fun registerUserFirebaseAuth(
        user: User,
        password: String,
        onSuccess: (String?) -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser? = task.result?.user
                    val firebaseUserId = firebaseUser?.uid.toString()
                    onSuccess(firebaseUserId)
                } else {
                    onFailure(task.exception?.message.toString())
                }
            }
    }

    override fun addUserToDb(
        user: User,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        firestoreDb.collection(FirebaseConstants.USERS_COLLECTION).document(user.id)
            .set(user, SetOptions.merge())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message.toString())
                }
            }
    }

    override suspend fun getUserData(userId: String): User? {
        val docRef = firestoreDb.collection(FirebaseConstants.USERS_COLLECTION)
            .document(userId)

        return docRef.get()
            .addOnSuccessListener { document ->
                Timber.d("getUserData Success: ${document.toObject(User::class.java)}")
            }
            .addOnFailureListener { exception ->
                Timber.d("getUserData Failed: $exception")
            }
            .await()
            .toObject(User::class.java)
    }

    override fun resetPasswordFirebaseAuth(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message.toString())
                }
            }
    }

    override suspend fun signInWithCredential(
        credential: AuthCredential,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess(task.result)
                } else {
                    onFailure(task.exception.toString())
                }
            }
            .await()
    }
}