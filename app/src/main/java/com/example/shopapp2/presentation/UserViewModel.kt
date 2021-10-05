package com.example.shopapp2.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp2.common.NetworkResult
import com.example.shopapp2.domain.models.User
import com.example.shopapp2.domain.use_case.LoginUserUseCase
import com.example.shopapp2.domain.use_case.UpdateUserDataUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserViewModel
@Inject
constructor(
    private val firebaseAuth: FirebaseAuth,
    private val loginUserUseCase: LoginUserUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase
) : ViewModel() {

    val user = mutableStateOf(User())
    val isLoading = mutableStateOf(false)

    init {
        getUserData()
    }

    private fun getUserData(
    ) {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val userid = firebaseUser.uid

            viewModelScope.launch {

                isLoading.value = true

                val getUserDataNetworkResult = userid.let { loginUserUseCase.getUserData(it) }
                when (getUserDataNetworkResult) {
                    is NetworkResult.Success ->
                        if (getUserDataNetworkResult.value != null) {
                            user.value = getUserDataNetworkResult.value
                        }
                    is NetworkResult.NetworkError -> Timber.d("getUserDataNetworkResult NetworkError")
                    is NetworkResult.GenericError -> Timber.d("getUserDataNetworkResult GenericErrore")
                }

                isLoading.value = false

            }

        }
    }

    fun updateUserData() {
        viewModelScope.launch {
            getUserData()
        }
    }

    fun updateUser(
        updatedDate: HashMap<String, Any>,
    ) {
        viewModelScope.launch {

            val firebaseUser = firebaseAuth.currentUser
            val userid = firebaseUser?.uid

            if (userid != null) {
                updateUserDataUseCase.updateUserData(
                    userId = userid,
                    updatedDate = updatedDate,
                    setLoadingState = ::setLoadingFun
                )
            }
        }
    }

    private fun setLoadingFun(boolean: Boolean) {
        // nothing
    }
}