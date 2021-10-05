package com.example.shopapp2.presentation.explore.clothes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.bookmarks.BookmarksListItem
import com.example.shopapp2.presentation.cart.MyCartViewModel
import com.example.shopapp2.presentation.components.AddToCartMedalSheetContent
import com.example.shopapp2.presentation.components.BookmarksListItemShimmer
import com.example.shopapp2.presentation.home.PAGE_SIZE
import com.example.shopapp2.presentation.product_detail.ProductDetailViewModel

@ExperimentalMaterialApi
@Composable
fun ClothesScreen(
    clothesViewModel: ClothesViewModel,
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            LazyColumn {

                if (clothesViewModel.isLoading.value) {
                    items(5) {
                        BookmarksListItemShimmer()
                    }
                }
                itemsIndexed(clothesViewModel.products) { index, product ->
                    clothesViewModel.onChangeListScrollPosition(index)

                    if ((index + 1) >= (clothesViewModel.page.value * PAGE_SIZE)
                        && !clothesViewModel.isNextPageLoading.value
                    ) {
                        clothesViewModel.loadNextPage()
                    }

                    BookmarksListItem(
                        product, state, scope, setProduct,
                        navController, productDetailViewModel
                    )

                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            if (clothesViewModel.isNextPageLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}