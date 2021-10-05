package com.example.shopapp2.domain.use_case

import com.example.shopapp2.common.NetworkResult
import com.example.shopapp2.common.safeNetworkCall
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.domain.repository.HomeRepository
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers

class GetDiscountsProductsUseCase(
    private val homeRepository: HomeRepository,
) {
    suspend fun getData(
        setDiscountsLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): NetworkResult<List<Product>?> {
        return safeNetworkCall(Dispatchers.IO) {
            homeRepository.getDiscountsProducts(setDiscountsLastVisibleDocumentSnapshot)
        }
    }

    suspend fun loadNextPage(
        product: DocumentSnapshot,
        setMostPopularLastVisibleDocumentSnapshot: (DocumentSnapshot) -> Unit
    ): NetworkResult<List<Product>?> {
        return safeNetworkCall(Dispatchers.IO) {
            homeRepository.loadDiscountsNextPage(
                product,
                setMostPopularLastVisibleDocumentSnapshot
            )
        }
    }
}