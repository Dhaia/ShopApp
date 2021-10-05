package com.example.shopapp2.domain.use_case

import com.example.shopapp2.common.NetworkResult
import com.example.shopapp2.common.safeNetworkCall
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.domain.repository.ExploreRepository
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers


class GetClothesProductsUseCase(
    private val exploreRepository: ExploreRepository,
) {
    suspend fun getData(
        setClothesLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): NetworkResult<List<Product>?> {
        return safeNetworkCall(Dispatchers.IO) {
            exploreRepository.getClothesProducts(setClothesLastVisibleDocumentSnapshot)
        }
    }

    suspend fun loadNextPage(
        product: DocumentSnapshot,
        setClothesLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): NetworkResult<List<Product>?> {
        return safeNetworkCall(Dispatchers.IO) {
            exploreRepository.loadClothesNextPage(
                product,
                setClothesLastVisibleDocumentSnapshot
            )
        }
    }
}