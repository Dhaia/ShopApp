package com.example.shopapp2.presentation.explore.clothes

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp2.common.NetworkResult
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.domain.use_case.GetClothesProductsUseCase
import com.example.shopapp2.presentation.home.PAGE_SIZE
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class ClothesViewModel
@Inject
constructor(
    private val getClothesProductsUseCase: GetClothesProductsUseCase
) : ViewModel() {

    val isNextPageLoading = mutableStateOf(false)
    val isLoading = mutableStateOf(false)

    val products = mutableStateListOf<Product>()

    private var lastVisibleDocument: DocumentSnapshot? = null

    val page = mutableStateOf(1)

    private var listScrollPosition = 0

    init {
        loadDataForFirstTime()
    }

    fun loadDataForFirstTime() {
        viewModelScope.launch {
            isLoading.value = true

            val mostPopularProductsNetworkResult = getClothesProductsUseCase.getData(
                ::setLastVisibleDocumentSnapshot
            )
            when (mostPopularProductsNetworkResult) {
                is NetworkResult.Success -> mostPopularProductsNetworkResult.value?.let {
                    appendProducts(
                        it
                    )
                }
                is NetworkResult.NetworkError -> Timber.d("NetworkError getMostPopularProductsUseCase")
                is NetworkResult.GenericError -> Timber.d("GenericError getMostPopularProductsUseCase")
            }

            isLoading.value = false
        }
    }

    fun loadNextPage() {
        viewModelScope.launch {
            if ((listScrollPosition + 1) >= (page.value * PAGE_SIZE)) {

                isNextPageLoading.value = true

                incrementPage()

                if (page.value > 1) {
                    val result = lastVisibleDocument?.let {
                        getClothesProductsUseCase
                            .loadNextPage(
                                it,
                                ::setLastVisibleDocumentSnapshot
                            )
                    }

                    when (result) {

                        is NetworkResult.Success -> result.value?.let {
                            appendProducts(it)
                        }
                        is NetworkResult.GenericError -> Timber.d("GenericError ${result.errorMessage}")
                        is NetworkResult.NetworkError -> Timber.d("Network Error")
                    }
                }

                isNextPageLoading.value = false

            }
        }
    }


    private fun appendProducts(list: List<Product>) {
        products.addAll(list)
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }

    private fun setLastVisibleDocumentSnapshot(documentSnapshot: DocumentSnapshot) {
        lastVisibleDocument = documentSnapshot
    }

    fun onChangeListScrollPosition(position: Int) {
        listScrollPosition = position
    }
}