package com.example.shopapp2.domain.repository

interface UserRepository {

    fun updateUserData(
        updatedDate: HashMap<String, Any>,
        userId: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    )

}