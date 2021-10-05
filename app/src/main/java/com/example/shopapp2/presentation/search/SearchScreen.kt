package com.example.shopapp2.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.bookmarks.BookmarksListItem
import com.example.shopapp2.presentation.cart.MyCartViewModel
import com.example.shopapp2.presentation.components.AddToCartMedalSheetContent
import com.example.shopapp2.presentation.product_detail.ProductDetailViewModel
import timber.log.Timber

@ExperimentalMaterialApi
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    navController: NavController,
    userViewModel: UserViewModel,
    productDetailViewModel: ProductDetailViewModel,
    myCartViewModel: MyCartViewModel
) {

    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val (product, setProduct) = remember { mutableStateOf(Product()) }

    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            AddToCartMedalSheetContent(product, state, scope, myCartViewModel, userViewModel)
        }
    ) {

        val (searchText, setSearchText) = rememberSaveable {
            mutableStateOf("")
        }
        searchViewModel.search(searchText)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            TextField(
                value = searchText,
                onValueChange = setSearchText,
                placeholder = {
                    Text(text = "Search for any products")
                }, maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(25.dp))

            LazyColumn {
                items(searchViewModel.searchResultList) { product ->

                    BookmarksListItem(
                        product, state, scope, setProduct,
                        navController, productDetailViewModel
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
        }
    }
}