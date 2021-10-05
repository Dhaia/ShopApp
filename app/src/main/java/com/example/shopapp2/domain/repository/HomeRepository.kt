package com.example.shopapp2.domain.repository

import com.example.shopapp2.domain.models.Product
import com.google.firebase.firestore.DocumentSnapshot

interface HomeRepository {

    suspend fun getMostPopularProducts(
        setMostPopularLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product>
    suspend fun loadMostPopularNextPage(
        product: DocumentSnapshot,
        setMostPopularLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product>

    suspend fun getDiscountsProducts(
        setDiscountsLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product>
    suspend fun loadDiscountsNextPage(
        product: DocumentSnapshot,
        setDiscountsLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product>

    suspend fun getNewProducts(
        setNewLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product>
    suspend fun loadNewNextPage(
        product: DocumentSnapshot,
        setNewLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product>

}