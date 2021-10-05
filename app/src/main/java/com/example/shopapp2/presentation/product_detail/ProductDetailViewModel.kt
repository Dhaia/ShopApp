package com.example.shopapp2.presentation.product_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.shopapp2.domain.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel
@Inject
constructor(
) : ViewModel() {

    private val _product = mutableStateOf(Product())
    val product: State<Product> = _product

    fun setProduct(product: Product) {
        _product.value = product
    }
}