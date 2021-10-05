package com.example.shopapp2.domain.use_case

import com.example.shopapp2.common.NetworkResult
import com.example.shopapp2.common.safeNetworkCall
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.domain.repository.ExploreRepository
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers

class GetShoesProductsUseCase(
    private val exploreRepository: ExploreRepository,
) {
    suspend fun getData(
        setShoesLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): NetworkResult<List<Product>?> {
        return safeNetworkCall(Dispatchers.IO) {
            exploreRepository.getShoesProducts(
                setShoesLastVisibleDocumentSnapshot
            )
        }
    }

    suspend fun loadNextPage(
        product: DocumentSnapshot,
        setShoesLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): NetworkResult<List<Product>?> {
        return safeNetworkCall(Dispatchers.IO) {
            exploreRepository.loadShoesNextPage(
                product,
                setShoesLastVisibleDocumentSnapshot
            )
        }
    }
}