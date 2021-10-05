package com.example.shopapp2.domain.use_case

import android.app.Application
import com.example.shopapp2.common.NetworkResult
import com.example.shopapp2.common.safeNetworkCall
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.domain.repository.HomeRepository
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers

class GetMostPopularProductsUseCase(
    private val homeRepository: HomeRepository,
    private val application: Application,
) {
    suspend fun getData(
        setMostPopularLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): NetworkResult<List<Product>?> {
        return safeNetworkCall(Dispatchers.IO) {
            homeRepository.getMostPopularProducts(setMostPopularLastVisibleDocumentSnapshot)
        }
    }

    suspend fun loadNextPage(
        product: DocumentSnapshot,
        setMostPopularLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): NetworkResult<List<Product>?> {
        return safeNetworkCall(Dispatchers.IO) {
            homeRepository.loadMostPopularNextPage(
                product,
                setMostPopularLastVisibleDocumentSnapshot
            )
        }
    }
}