package com.example.shopapp2.presentation.home.see_all.most_popular

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
import com.example.shopapp2.presentation.home.HomeViewModel
import com.example.shopapp2.presentation.home.PAGE_SIZE
import com.example.shopapp2.presentation.product_detail.ProductDetailViewModel

@ExperimentalMaterialApi
@Composable
fun MostPopularSeeAllScreen(
    homeViewModel: HomeViewModel,
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
                itemsIndexed(homeViewModel.mostPopularProducts) { index, product ->
                    homeViewModel.onChangeMostPopularScrollPosition(index)

                    if ((index + 1) >= (homeViewModel.mostPopularPage.value * PAGE_SIZE)
                        && !homeViewModel.isLoading.value
                    ) {
                        homeViewModel.loadMostPopularNextPage()
                    }

                    BookmarksListItem(
                        product, state, scope, setProduct,
                        navController, productDetailViewModel
                    )

                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            if (homeViewModel.isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Spacer(modifier = Modifier.height(20.dp))

        }
    }
}