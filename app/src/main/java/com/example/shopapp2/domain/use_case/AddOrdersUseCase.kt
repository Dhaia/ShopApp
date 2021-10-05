package com.example.shopapp2.domain.use_case

import android.app.Application
import android.widget.Toast
import androidx.navigation.NavController
import com.example.shopapp2.domain.models.Order
import com.example.shopapp2.domain.repository.OrdersRepository
import com.example.shopapp2.presentation.util.NavigationScreens

class AddOrdersUseCase(
    private val ordersRepository: OrdersRepository,
    private val application: Application,
) {

    suspend fun addOrders(
        list: MutableList<Order>,
        navController: NavController,
        setLoadingState: (Boolean) -> Unit
    ) {

        fun onSuccess() {
            setLoadingState(false)
            navController.popBackStack()
            navController.navigate(NavigationScreens.OrderCompletedScreen.route)
        }

        fun onFailure(error: String?) {
            setLoadingState(false)

            Toast.makeText(
                application.applicationContext,
                "There was an error: $error", Toast.LENGTH_LONG
            ).show()
        }

        ordersRepository.addOrders(
            ordersList = list,
            onSuccess = ::onSuccess,
            onFailure = ::onFailure
        )
    }
}