package com.example.shopapp2.domain.repository

import com.example.shopapp2.domain.models.Product
import com.google.firebase.firestore.DocumentSnapshot

interface ExploreRepository {

    suspend fun getClothesProducts(
        setClothesLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product>

    suspend fun loadClothesNextPage(
        product: DocumentSnapshot,
        setClothesLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product>

    suspend fun getShoesProducts(
        setShoesLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product>

    suspend fun loadShoesNextPage(
        product: DocumentSnapshot,
        setShoesLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product>

    suspend fun getWatchesProducts(
        setWatchesLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product>

    suspend fun loadWatchesNextPage(
        product: DocumentSnapshot,
        setWatchesLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): List<Product>

}