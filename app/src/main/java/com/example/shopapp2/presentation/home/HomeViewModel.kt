package com.example.shopapp2.presentation.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp2.common.NetworkResult
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.domain.use_case.GetDiscountsProductsUseCase
import com.example.shopapp2.domain.use_case.GetMostPopularProductsUseCase
import com.example.shopapp2.domain.use_case.GetNewProductsUseCase
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

const val PAGE_SIZE = 4

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val getMostPopularProductsUseCase: GetMostPopularProductsUseCase,
    private val getDiscountsProductsUseCase: GetDiscountsProductsUseCase,
    private val getNewProductsUseCase: GetNewProductsUseCase,
) : ViewModel() {

    val isLoading = mutableStateOf(false)

    val isPopularProductsLoading = mutableStateOf(false)
    val isDiscountsProductsLoading = mutableStateOf(false)
    val isNewProductsLoading = mutableStateOf(false)

    val mostPopularProducts = mutableStateListOf<Product>()
    val discountsProducts = mutableStateListOf<Product>()
    val newProducts = mutableStateListOf<Product>()

    private var mostPopularLastVisibleDocument: DocumentSnapshot? = null
    private var discountsLastLastVisibleDocument: DocumentSnapshot? = null
    private var newLastLastVisibleDocument: DocumentSnapshot? = null

    val mostPopularPage = mutableStateOf(1)
    val discountsPage = mutableStateOf(1)
    val newPage = mutableStateOf(1)

    private var mostPopularListScrollPosition = 0
    private var discountsListScrollPosition = 0
    private var newListScrollPosition = 0

    init {

        isPopularProductsLoading.value = true
        isNewProductsLoading.value = true
        isDiscountsProductsLoading.value = true

        loadDataForFirstTime()
    }

    fun loadDataForFirstTime() {
        viewModelScope.launch {
            val mostPopularProductsNetworkResult = getMostPopularProductsUseCase.getData(
                ::setMostPopularLastVisibleDocumentSnapshot
            )
            when (mostPopularProductsNetworkResult) {
                is NetworkResult.Success -> {
                    mostPopularProductsNetworkResult.value?.let {
                        appendMostPopularProducts(
                            it
                        )
                    }
                }
                is NetworkResult.NetworkError -> {
                    Timber.d("NetworkError")
                }
                is NetworkResult.GenericError -> {
                    Timber.d("GenericError")
                }
            }
            isPopularProductsLoading.value = false

            val discountsProductsNetworkResult =
                getDiscountsProductsUseCase.getData(::setDiscountsLastVisibleDocumentSnapshot)
            when (discountsProductsNetworkResult) {
                is NetworkResult.Success -> discountsProductsNetworkResult.value?.let {
                    appendDiscountsProducts(
                        it
                    )
                }
                is NetworkResult.NetworkError -> Timber.d("NetworkError")
                is NetworkResult.GenericError -> Timber.d("GenericError")
            }
            isDiscountsProductsLoading.value = false

            val newProductsNetworkResult =
                getNewProductsUseCase.getData(::setNewLastVisibleDocumentSnapshot)
            when (newProductsNetworkResult) {
                is NetworkResult.Success -> newProductsNetworkResult.value?.let {
                    appendNewProducts(
                        it
                    )
                }
                is NetworkResult.NetworkError -> Timber.d("NetworkError")
                is NetworkResult.GenericError -> Timber.d("GenericError")
            }
            isNewProductsLoading.value = false
        }
    }

    private fun appendMostPopularProducts(productsList: List<Product>) {
        mostPopularProducts.addAll(productsList)
    }

    private fun appendDiscountsProducts(productsList: List<Product>) {
        discountsProducts.addAll(productsList)
    }

    private fun appendNewProducts(productsList: List<Product>) {
        newProducts.addAll(productsList)
    }

    fun loadMostPopularNextPage() {
        viewModelScope.launch {

            isPopularProductsLoading.value = true

            if ((mostPopularListScrollPosition + 1) >= (mostPopularPage.value * PAGE_SIZE)) {

                incrementMostPopularPage()

                if (mostPopularPage.value > 1) {
                    val result = mostPopularLastVisibleDocument?.let {
                        getMostPopularProductsUseCase
                            .loadNextPage(
                                it,
                                ::setMostPopularLastVisibleDocumentSnapshot
                            )
                    }
                    when (result) {
                        is NetworkResult.Success -> result.value?.let {
                            appendMostPopularProducts(it)
                        }
                        is NetworkResult.GenericError -> Timber.d("${result.errorMessage}")
                        is NetworkResult.NetworkError -> Timber.d("Network Error")
                    }
                }

                isPopularProductsLoading.value = false
            }
        }
    }

    fun loadDiscountsNextPage() {
        viewModelScope.launch {

            isDiscountsProductsLoading.value = true

            if ((discountsListScrollPosition + 1) >= (discountsPage.value * PAGE_SIZE)) {

                incrementDiscountsPage()

                if (discountsPage.value > 1) {
                    // Call load next in repo
                    val result = discountsLastLastVisibleDocument?.let {
                        getDiscountsProductsUseCase
                            .loadNextPage(
                                it,
                                ::setDiscountsLastVisibleDocumentSnapshot
                            )
                    }

                    when (result) {

                        is NetworkResult.Success -> result.value?.let {
                            appendDiscountsProducts(it)
                        }
                        is NetworkResult.GenericError -> Timber.d("${result.errorMessage}")
                        is NetworkResult.NetworkError -> Timber.d("Network Error")
                    }
                }

                isDiscountsProductsLoading.value = false

            }
        }
    }

    fun loadNewNextPage() {
        viewModelScope.launch {

            isNewProductsLoading.value = true

            if ((newListScrollPosition + 1) >= (newPage.value * PAGE_SIZE)) {

                incrementNewPage()

                if (newPage.value > 1) {
                    val result = newLastLastVisibleDocument?.let {
                        getNewProductsUseCase
                            .loadNextPage(
                                it,
                                ::setNewLastVisibleDocumentSnapshot
                            )
                    }

                    when (result) {
                        is NetworkResult.Success -> result.value?.let {
                            appendNewProducts(it)
                        }
                        is NetworkResult.GenericError -> Timber.d("${result.errorMessage}")
                        is NetworkResult.NetworkError -> Timber.d("Network Error")
                    }
                }

                isNewProductsLoading.value = false
            }
        }
    }

    private fun incrementMostPopularPage() {
        mostPopularPage.value = mostPopularPage.value + 1
    }

    private fun incrementDiscountsPage() {
        discountsPage.value = discountsPage.value + 1
    }

    private fun incrementNewPage() {
        newPage.value = newPage.value + 1
    }

    private fun setMostPopularLastVisibleDocumentSnapshot(documentSnapshot: DocumentSnapshot) {
        mostPopularLastVisibleDocument = documentSnapshot
    }

    private fun setDiscountsLastVisibleDocumentSnapshot(documentSnapshot: DocumentSnapshot) {
        discountsLastLastVisibleDocument = documentSnapshot
    }

    private fun setNewLastVisibleDocumentSnapshot(documentSnapshot: DocumentSnapshot) {
        newLastLastVisibleDocument = documentSnapshot
    }

    fun onChangeMostPopularScrollPosition(position: Int) {
        mostPopularListScrollPosition = position
    }

    fun onChangeDiscountsScrollPosition(position: Int) {
        discountsListScrollPosition = position
    }

    fun onChangeNewScrollPosition(position: Int) {
        newListScrollPosition = position
    }
}