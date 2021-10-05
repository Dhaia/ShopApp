package com.example.shopapp2.domain.repository

import com.example.shopapp2.domain.models.Order

interface OrdersRepository {

    suspend fun addOrders(
        ordersList: MutableList<Order>,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    )

}