package com.example.shopapp2.domain.use_case

import com.example.shopapp2.common.NetworkResult
import com.example.shopapp2.common.safeNetworkCall
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.domain.repository.HomeRepository
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers

class GetNewProductsUseCase (
    private val homeRepository: HomeRepository,
) {
    suspend fun getData(
        setNewLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): NetworkResult<List<Product>?> {
        return safeNetworkCall(Dispatchers.IO) {
            homeRepository.getNewProducts(setNewLastVisibleDocumentSnapshot)
        }
    }

    suspend fun loadNextPage(
        product: DocumentSnapshot,
        setMostPopularLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): NetworkResult<List<Product>?>{
        return safeNetworkCall(Dispatchers.IO) {
            homeRepository.loadNewNextPage(product,
                setMostPopularLastVisibleDocumentSnapshot)
        }
    }
}



