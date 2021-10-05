package com.example.shopapp2.domain.use_case

import android.app.Application
import android.widget.Toast
import androidx.navigation.NavController
import com.example.shopapp2.common.NetworkResult
import com.example.shopapp2.common.safeNetworkCall
import com.example.shopapp2.domain.models.User
import com.example.shopapp2.domain.repository.LoginRepository
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.explore.clothes.ClothesViewModel
import com.example.shopapp2.presentation.explore.shoes.ShoesViewModel
import com.example.shopapp2.presentation.explore.watches.WatchesViewModel
import com.example.shopapp2.presentation.home.HomeViewModel
import com.example.shopapp2.presentation.util.NavigationScreens
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers

class LoginUserUseCase(
    private val loginRepository: LoginRepository,
    private val application: Application,
) {

    suspend fun getUserData(userId: String): NetworkResult<User?> {
        return safeNetworkCall(Dispatchers.IO) {
            loginRepository.getUserData(userId)
        }
    }

    fun loginUser(
        email: String,
        password: String,
        navController: NavController,
        setLoadingState: (Boolean) -> Unit,
        userViewModel: UserViewModel,
        homeViewModel: HomeViewModel,

        watchesViewModel: WatchesViewModel,
        clothesViewModel: ClothesViewModel,
        shoesViewModel: ShoesViewModel
    ) {

        fun onSuccess(userId: String?) {
            setLoadingState(false)
            Toast.makeText(
                application.applicationContext,
                "Signed in successfully $userId",
                Toast.LENGTH_LONG
            ).show()

            userViewModel.updateUserData()
            homeViewModel.loadDataForFirstTime()
            watchesViewModel.loadDataForFirstTime()
            shoesViewModel.loadDataForFirstTime()
            clothesViewModel.loadDataForFirstTime()
            navController.navigate(NavigationScreens.HomeScreen.route) {
                popUpTo(route = NavigationScreens.LoginScreen.route) {
                    inclusive = true
                }
            }
        }

        fun onFailure(error: String?) {
            setLoadingState(false)
            Toast.makeText(application.applicationContext, error, Toast.LENGTH_LONG).show()
        }

        loginRepository.loginUserWithEmailAndPassword(
            email = email,
            password = password,
            ::onSuccess,
            ::onFailure
        )
    }

    fun registerNewUser(
        user: User,
        password: String,
        navController: NavController,
        setLoadingState: (Boolean) -> Unit,
        userViewModel: UserViewModel,
        homeViewModel: HomeViewModel,

        watchesViewModel: WatchesViewModel,
        clothesViewModel: ClothesViewModel,
        shoesViewModel: ShoesViewModel
    ) {
        fun onStoreUserDataSuccess() {
            setLoadingState(false)

            Toast.makeText(
                application.applicationContext,
                "User registered successfully",
                Toast.LENGTH_LONG
            ).show()

            userViewModel.updateUserData()
            homeViewModel.loadDataForFirstTime()
            watchesViewModel.loadDataForFirstTime()
            shoesViewModel.loadDataForFirstTime()
            clothesViewModel.loadDataForFirstTime()
            navController.popBackStack()
            navController.navigate(NavigationScreens.HomeScreen.route)
        }

        fun onStoreUserDataFailure(error: String?) {
            setLoadingState(false)

            Toast.makeText(
                application.applicationContext,
                error, Toast.LENGTH_LONG
            ).show()
        }

        fun onRegisterSuccess(userId: String?) {
            if (userId != null) {
                user.id = userId

                // Store the user data in Firestore
                loginRepository.addUserToDb(
                    user = user,
                    ::onStoreUserDataSuccess,
                    ::onStoreUserDataFailure
                )
            }
        }

        fun onRegisterFailure(error: String?) {
            Toast.makeText(application.applicationContext, error, Toast.LENGTH_LONG).show()
            setLoadingState(false)
        }

        loginRepository.registerUserFirebaseAuth(
            user = user,
            password = password,
            ::onRegisterSuccess,
            ::onRegisterFailure
        )
    }

    fun resetPassword(
        navController: NavController,
        email: String,
        setLoadingState: (Boolean) -> Unit
    ) {
        fun onResetPasswordSuccess() {
            setLoadingState(false)

            Toast.makeText(
                application.applicationContext,
                "Email Sent successfully",
                Toast.LENGTH_LONG
            )
                .show()
            navController.navigate(NavigationScreens.EmailSentScreen.route)
        }

        fun onResetPasswordFailure(error: String?) {
            setLoadingState(false)

            Toast.makeText(application.applicationContext, error, Toast.LENGTH_LONG).show()
        }

        loginRepository.resetPasswordFirebaseAuth(
            onSuccess = ::onResetPasswordSuccess,
            onFailure = ::onResetPasswordFailure,
            email = email
        )
    }

    suspend fun signInWithCredential(
        navController: NavController,
        credential: AuthCredential,
        setLoadingState: (Boolean) -> Unit,
        userViewModel: UserViewModel,
        homeViewModel: HomeViewModel,

        watchesViewModel: WatchesViewModel,
        clothesViewModel: ClothesViewModel,
        shoesViewModel: ShoesViewModel
    ) {
        fun onStoreUserDataSuccess() {
            setLoadingState(false)
            Toast.makeText(
                application.applicationContext,
                "User signed in successfully",
                Toast.LENGTH_LONG
            ).show()

            homeViewModel.loadDataForFirstTime()

            watchesViewModel.loadDataForFirstTime()
            shoesViewModel.loadDataForFirstTime()
            clothesViewModel.loadDataForFirstTime()

            userViewModel.updateUserData()
            navController.popBackStack()
            navController.navigate(NavigationScreens.HomeScreen.route)
        }

        fun onStoreUserDataFailure(error: String?) {
            setLoadingState(false)
            Toast.makeText(
                application.applicationContext,
                "Failed to upload user data $error",
                Toast.LENGTH_LONG
            ).show()
        }

        fun onSuccess(authResult: AuthResult) {
            // Check if the user is new
            if (authResult.additionalUserInfo?.isNewUser == true) {

                val uid = authResult.user?.uid
                val email = authResult.user?.email

                val user = User()
                if (uid != null) {
                    user.id = uid
                }
                if (email != null) {
                    user.email = email
                }

                if (user.id.isNotEmpty() && user.email.isNotEmpty()) {
                    loginRepository.addUserToDb(
                        user = user,
                        ::onStoreUserDataSuccess,
                        ::onStoreUserDataFailure
                    )
                } else {
                    setLoadingState(false)
                    Toast.makeText(
                        application.applicationContext, "Failed to sign in: empty user data",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            // The user already exists
            else {
                setLoadingState(false)
                Toast.makeText(
                    application.applicationContext, "User Signed in successfully",
                    Toast.LENGTH_LONG
                ).show()

                homeViewModel.loadDataForFirstTime()
                watchesViewModel.loadDataForFirstTime()
                shoesViewModel.loadDataForFirstTime()
                clothesViewModel.loadDataForFirstTime()
                userViewModel.updateUserData()
                navController.popBackStack()
                navController.navigate(NavigationScreens.HomeScreen.route)
            }
        }

        fun onFailure(error: String?) {
            setLoadingState(false)
            Toast.makeText(
                application.applicationContext,
                "Failed to sign in with credential $error",
                Toast.LENGTH_LONG
            ).show()
        }

        loginRepository.signInWithCredential(
            credential,
            ::onSuccess,
            ::onFailure
        )
    }
}
