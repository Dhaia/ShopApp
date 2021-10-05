package com.example.shopapp2.presentation.checkout

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.shopapp2.domain.models.Order
import com.example.shopapp2.domain.use_case.AddOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel
@Inject
constructor(
    private val addOrdersUseCase: AddOrdersUseCase
) : ViewModel() {

    val isLoading = mutableStateOf(false)

    private fun setLoadingState(boolean: Boolean){
        isLoading.value = boolean
    }

    fun addOrders(list: MutableList<Order>, navController: NavController) {
        viewModelScope.launch {
            setLoadingState(true)
            addOrdersUseCase.addOrders(
                list = list,
                navController = navController,
                ::setLoadingState
            )
        }
    }
}