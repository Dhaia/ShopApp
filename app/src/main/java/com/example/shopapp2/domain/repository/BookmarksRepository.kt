package com.example.shopapp2.domain.repository

import com.example.shopapp2.domain.models.Product

interface BookmarksRepository {

    suspend fun addBookmark(
        product: Product,
        userId: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,)

    suspend fun deleteBookmark(
        product: Product,
        userId: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,)

}