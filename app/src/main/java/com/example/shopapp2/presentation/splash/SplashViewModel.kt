package com.example.shopapp2.presentation.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel
@Inject
constructor(
    firebaseAuth: FirebaseAuth
) : ViewModel() {

    val isSignedIn = mutableStateOf(false)

    init {
        val currentUser = firebaseAuth.currentUser
        isSignedIn.value = currentUser != null
    }
}