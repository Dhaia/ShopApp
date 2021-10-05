package com.example.shopapp2.presentation.product_detail

import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.bookmarks.BookmarksViewModel
import com.example.shopapp2.presentation.cart.MyCartViewModel
import com.example.shopapp2.presentation.components.AddToCartMedalSheetContent
import com.example.shopapp2.presentation.components.addBookmark
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun ProductDetailScreen(
    productDetailViewModel: ProductDetailViewModel,
    myCartViewModel: MyCartViewModel,
    userViewModel: UserViewModel,
    bookmarksViewModel: BookmarksViewModel
) {
    val productDetail = productDetailViewModel.product.value

    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            AddToCartMedalSheetContent(
                productDetail, state, scope,
                myCartViewModel = myCartViewModel,
                userViewModel = userViewModel
            )
        }
    ) {
        ShowContent(
            productDetail, state, scope,
            bookmarksViewModel = bookmarksViewModel,
            userViewModel = userViewModel
        )
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
private fun ShowContent(
    product: Product?,
    state: ModalBottomSheetState,
    scope: CoroutineScope,
    bookmarksViewModel: BookmarksViewModel,
    userViewModel: UserViewModel,
) {

    val context = LocalContext.current
    val tint = remember {
        mutableStateOf(false)
    }
    val isBookmarked =
        userViewModel.user.value.bookmarksProducts.find { it.id == product?.id }
    tint.value = isBookmarked != null

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val boxWithConstraintsScope = this

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colors.background)
        ) {

            if (product?.imagesUrls != null) {

                val pagerState = rememberPagerState(pageCount = product.imagesUrls.size)

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) { page ->
                    Image(
                        painter = rememberImagePainter(Uri.parse(product.imagesUrls[page])),
                        contentDescription = "product images",
                        modifier = Modifier
                            .height(boxWithConstraintsScope.maxHeight * .45f)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(bottomStart = 35.dp, bottomEnd = 35.dp)),
                        contentScale = ContentScale.Crop,
                    )
                }
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp, bottom = 2.dp),
                    indicatorShape = RoundedCornerShape(50.dp),
                    activeColor = MaterialTheme.colors.primary
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    // Title and Bookmark Icon
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (product != null) {
                            Text(
                                text = product.title,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier
                                    .padding(start = 2.dp)
                                    .fillMaxWidth(0.85f)
                            )
                        }

                        Icon(
                            if (tint.value)
                                Icons.Filled.BookmarkAdded
                            else
                                Icons.Filled.BookmarkBorder,
                            contentDescription = "Add bookmark",
                            Modifier
                                .size(30.dp)
                                .clickable {
                                    if (tint.value) {

                                        // Delete the bookmark
                                        if (isBookmarked != null) {
                                            bookmarksViewModel.deleteBookmark(isBookmarked)
                                            userViewModel.updateUserData()
                                        }
                                    } else {
                                        if (product != null) {
                                            addBookmark(
                                                product, bookmarksViewModel, userViewModel,
                                                context
                                            )
                                        }
                                    }
                                },
                            tint =
                            if (tint.value)
                                MaterialTheme.colors.primary
                            else
                                MaterialTheme.colors.onSurface.copy(0.4f)
                        )
                    }

                    Spacer(modifier = Modifier.padding(15.dp))

                    // Description
                    Text(
                        text = "Description",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onBackground.copy(0.8f)
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    product?.let {
                        Text(
                            text = it.description,
                            color = MaterialTheme.colors.onBackground.copy(0.6f)
                        )
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    // Price
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Price:", fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colors.onBackground.copy(0.8f)
                        )
                        Spacer(modifier = Modifier.padding(3.dp))
                        if (product != null) {
                            if (!product.discountBoolean) {
                                Text(
                                    text = "$${product.price.toString()}",
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colors.primary
                                )
                            } else {
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "$${product.price.toString()}",
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colors.primary.copy(0.8f),
                                        style = TextStyle(
                                            textDecoration = TextDecoration.LineThrough
                                        ),
                                    )
                                    Spacer(modifier = Modifier.padding(3.dp))
                                    Text(
                                        text = "$${product.discount}",
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colors.primary,
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    // Available sizes
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Available sizes:",
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colors.onBackground.copy(0.8f)
                        )
                        Spacer(modifier = Modifier.padding(3.dp))
                        Text(
                            product?.sizes.toString()
                                .replace("[", "")
                                .replace("]", ""),
                            fontWeight = FontWeight.SemiBold,
                            )
                    }

                    Spacer(modifier = Modifier.padding(5.dp))

                    // Available colors
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Available colors:",
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colors.onBackground.copy(0.8f)
                        )
                        Spacer(modifier = Modifier.padding(3.dp))
                        Text(
                            product?.colors.toString()
                                .replace("[", "")
                                .replace("]", ""),
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                    Spacer(modifier = Modifier.padding(30.dp))

                    Button(
                        onClick = {
                            scope.launch { state.show() }
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Add to Cart",
                            color = MaterialTheme.colors.onBackground.copy(0.83f)
                        )
                    }
                }
            }
        }
    }
}