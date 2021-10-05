package com.example.shopapp2.presentation.bookmarks

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.cart.MyCartViewModel
import com.example.shopapp2.presentation.components.AddToCartMedalSheetContent
import com.example.shopapp2.presentation.components.BookmarksListItemShimmer
import com.example.shopapp2.presentation.product_detail.ProductDetailViewModel
import com.example.shopapp2.presentation.theme.Purple500
import com.example.shopapp2.presentation.util.NavigationScreens
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun BookmarksScreen(
    navController: NavController,
    bookmarksViewModel: BookmarksViewModel,
    myCartViewModel: MyCartViewModel,
    userViewModel: UserViewModel,
    productDetailViewModel: ProductDetailViewModel
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "My Bookmarks",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.padding(10.dp))

            LazyColumn {
                if (userViewModel.isLoading.value) {
                    items(6) {
                        BookmarksListItemShimmer()

                        Spacer(modifier = Modifier.padding(5.dp))
                    }
                }
                items(userViewModel.user.value.bookmarksProducts) { product ->

                    RevealSwipe(
                        modifier = Modifier.padding(vertical = 5.dp),
                        directions = setOf(
                            RevealDirection.StartToEnd,
                            RevealDirection.EndToStart
                        ),
                        hiddenContentStart = {
                            Icon(
                                modifier = Modifier.padding(horizontal = 25.dp),
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = null,
                            )
                        },
                        hiddenContentEnd = {
                            Icon(
                                modifier = Modifier.padding(horizontal = 25.dp),
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = null
                            )
                        },
                        onBackgroundEndClick = {
                            bookmarksViewModel.deleteBookmark(product)
                            userViewModel.updateUserData()
                        },
                        onBackgroundStartClick = {
                            bookmarksViewModel.deleteBookmark(product)
                            userViewModel.updateUserData()
                        },
                        backgroundCardStartColor = MaterialTheme.colors.secondary
                    ) {
                        BookmarksListItem(
                            product, state, scope, setProduct,
                            navController, productDetailViewModel
                        )
                    }

                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }

        }
    }
}

@ExperimentalMaterialApi
@Composable
fun BookmarksListItem(
    product: Product,
    state: ModalBottomSheetState,
    scope: CoroutineScope,
    setProduct: (Product) -> Unit,
    navController: NavController,
    productDetailViewModel: ProductDetailViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colors.surface)
            .clickable {
                productDetailViewModel.setProduct(product)
                if (product.id.isNotEmpty()) {
                    navController.navigate(
                        NavigationScreens.ProductDetailScreen.route
                    )
                }
            }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            if (product.imagesUrls.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(Uri.parse(product.imagesUrls[0])),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.4f)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                Modifier
                    .padding(start = 8.dp, top = 3.dp, bottom = 3.dp)
            ) {

                // Title
                Text(
                    text = product.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                )
                Spacer(modifier = Modifier.padding(2.dp))

                // Description
                Text(
                    text = product.description,
                    fontSize = 13.sp,
                    color = MaterialTheme.colors.onSurface.copy(0.7f),
                    maxLines = 2,
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${product.price.toString()}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Purple500
                    )
                    Button(
                        onClick = {
                            setProduct(product)
                            scope.launch { state.show() }
                        },
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .clip(RoundedCornerShape(20.dp))
                    ) {
                        Text(
                            text = "Add to cart",
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }
    }
}












