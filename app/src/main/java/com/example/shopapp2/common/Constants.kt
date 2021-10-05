package com.example.shopapp2.common

object FirebaseConstants {
    const val USERS_COLLECTION = "users"
    const val PRODUCTS_COLLECTION = "products"
    const val ORDERS_COLLECTION = "orders"
}
object ProductTypesConstants {
    const val CLOTHES = "clothes"
    const val WATCHES = "watches"
    const val SHOES = "shoes"
}

object GenericErrors {
    const val ERROR_UNKNOWN = "Unknown error"
}
object NetworkErrors {
    const val ERROR_CHECK_NETWORK_CONNECTION = "Check network connection."
    const val NETWORK_ERROR_UNKNOWN = "Unknown network error"
    const val NETWORK_ERROR_TIMEOUT = "Network timeout"
}