package com.example.shopapp2.presentation.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.shopapp2.domain.use_case.UpdateUserDataUseCase
import com.example.shopapp2.presentation.util.NavigationScreens
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val firebaseAuth: FirebaseAuth,
    private val updateUserDataUseCase: UpdateUserDataUseCase
) : ViewModel() {

    val isLoading = mutableStateOf(false)

    fun logUserOut(navController: NavController) {
        firebaseAuth.signOut()
        navController.navigate(NavigationScreens.LoginScreen.route) {
            popUpTo(route = NavigationScreens.ProfileScreen.route) {
                inclusive = true
            }
        }
    }

    private fun setLoadingState(boolean: Boolean) {
        isLoading.value = boolean
    }

    fun updateUserData(
        updatedDate: HashMap<String, Any>,
    ) {
        setLoadingState(true)

        val currentUser = firebaseAuth.currentUser

        viewModelScope.launch {

            if (currentUser != null) {
                updateUserDataUseCase.updateUserData(
                    updatedDate = updatedDate,
                    userId = currentUser.uid,
                    ::setLoadingState
                )
            }
        }

    }

}