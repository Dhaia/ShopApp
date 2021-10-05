package com.example.shopapp2.domain.use_case

import android.app.Application
import android.widget.Toast
import com.example.shopapp2.domain.repository.UserRepository

class UpdateUserDataUseCase(
    private val userRepository: UserRepository,
    private val application: Application,
) {

    fun updateUserData(
        updatedDate: HashMap<String, Any>,
        userId: String,
        setLoadingState: (Boolean) -> Unit,
    ) {

        fun onSuccess() {
            setLoadingState(false)
            Toast.makeText(
                application.applicationContext,
                "Your data has been updated", Toast.LENGTH_LONG
            ).show()
        }

        fun onFailure(error: String?) {
            setLoadingState(false)

            Toast.makeText(
                application.applicationContext,
                "Couldn't update data $error", Toast.LENGTH_LONG
            ).show()
        }

        userRepository.updateUserData(
            updatedDate = updatedDate,
            userId = userId,
            onFailure = ::onFailure,
            onSuccess = ::onSuccess
        )
    }

}