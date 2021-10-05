package com.example.shopapp2.presentation.login

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.shopapp2.domain.models.User
import com.example.shopapp2.domain.use_case.LoginUserUseCase
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.explore.clothes.ClothesViewModel
import com.example.shopapp2.presentation.explore.shoes.ShoesViewModel
import com.example.shopapp2.presentation.explore.watches.WatchesViewModel
import com.example.shopapp2.presentation.home.HomeViewModel
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val application: Application,
    private val loginUserUseCase: LoginUserUseCase,
) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private fun setLoadingState(boolean: Boolean) {
        _isLoading.value = boolean
    }

    fun loginUser(
        email: String,
        password: String,
        navController: NavController,
        userViewModel: UserViewModel,
        homeViewModel: HomeViewModel,

        watchesViewModel: WatchesViewModel,
        clothesViewModel: ClothesViewModel,
        shoesViewModel: ShoesViewModel
    ) {
        _isLoading.value = true
        loginUserUseCase.loginUser(
            email, password, navController, ::setLoadingState, userViewModel,
            homeViewModel,
            watchesViewModel = watchesViewModel,
            clothesViewModel = clothesViewModel,
            shoesViewModel = shoesViewModel
        )
    }

    fun registerNewUser(
        user: User,
        password: String,
        navController: NavController,
        userViewModel: UserViewModel,
        homeViewModel: HomeViewModel,

        watchesViewModel: WatchesViewModel,
        clothesViewModel: ClothesViewModel,
        shoesViewModel: ShoesViewModel
    ) {
        _isLoading.value = true
        loginUserUseCase.registerNewUser(
            user, password, navController, ::setLoadingState, userViewModel = userViewModel,
            homeViewModel,
            watchesViewModel = watchesViewModel,
            clothesViewModel = clothesViewModel,
            shoesViewModel = shoesViewModel
        )
    }

    fun resetPassword(
        email: String,
        navController: NavController
    ) {
        _isLoading.value = true
        loginUserUseCase.resetPassword(
            navController = navController,
            email = email,
            setLoadingState = ::setLoadingState
        )
    }

    fun signWithCredential(
        credential: AuthCredential,
        userViewModel: UserViewModel,
        navController: NavController,
        homeViewModel: HomeViewModel,
        watchesViewModel: WatchesViewModel,
        clothesViewModel: ClothesViewModel,
        shoesViewModel: ShoesViewModel
    ) {
        try {
            viewModelScope.launch {
                setLoadingState(true)
                loginUserUseCase.signInWithCredential(
                    navController = navController,
                    userViewModel = userViewModel,
                    credential = credential,
                    setLoadingState = ::setLoadingState,
                    homeViewModel = homeViewModel,
                    watchesViewModel = watchesViewModel,
                    clothesViewModel = clothesViewModel,
                    shoesViewModel = shoesViewModel
                )
            }
        } catch (e: Exception) {
            Toast.makeText(
                application.applicationContext, "Failed to Sing in: ${e.localizedMessage}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}