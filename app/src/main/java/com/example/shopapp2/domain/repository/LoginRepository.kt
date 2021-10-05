package com.example.shopapp2.domain.repository

import com.example.shopapp2.domain.models.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult


interface LoginRepository {

    fun loginUserWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: (String?) -> Unit,
        onFailure: (String?) -> Unit,
    )

    fun registerUserFirebaseAuth(
        user: User,
        password: String,
        onSuccess: (String?) -> Unit,
        onFailure: (String?) -> Unit,
    )

    fun addUserToDb(
        user: User,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    )

    suspend fun getUserData(
        userId: String
    ): User?

    fun resetPasswordFirebaseAuth(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    )

    suspend fun signInWithCredential(
        credential: AuthCredential,
        onSuccess: (AuthResult) -> Unit,
        onFailure: (String?) -> Unit
    )
}