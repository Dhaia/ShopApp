package com.example.shopapp2.presentation.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import com.example.shopapp2.R
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.bookmarks.BookmarksViewModel
import com.example.shopapp2.presentation.components.ShoppingListItem
import com.example.shopapp2.presentation.components.ShoppingListItemShimmer
import com.example.shopapp2.presentation.product_detail.ProductDetailViewModel
import com.example.shopapp2.presentation.util.NavigationScreens
import com.google.accompanist.pager.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.math.absoluteValue
import kotlin.time.ExperimentalTime

@ExperimentalFoundationApi
@ExperimentalTime
@ExperimentalPagerApi
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
    productDetailViewModel: ProductDetailViewModel,
    bookmarksViewModel: BookmarksViewModel,
    userViewModel: UserViewModel,
) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.surface,
        darkIcons = useDarkIcons
    )

    ShowContent(
        navController, productDetailViewModel, homeViewModel,
        bookmarksViewModel, userViewModel
    )
}

@ExperimentalFoundationApi
@ExperimentalTime
@ExperimentalPagerApi
@Composable
private fun ShowContent(
    navController: NavController,
    productDetailViewModel: ProductDetailViewModel,
    homeViewModel: HomeViewModel,
    bookmarksViewModel: BookmarksViewModel,
    userViewModel: UserViewModel
) {

    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {

        Pager()

        Spacer(modifier = Modifier.padding(9.dp))

        // Search for products Field
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    navController.navigate(NavigationScreens.SearchScreen.route)
                }
                .fillMaxWidth(0.77f)
                .align(Alignment.CenterHorizontally)
                .background(MaterialTheme.colors.surface.copy(0.7f))
                .padding(15.dp)
        ) {
            Icon(
                Icons.Filled.Search,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "Search for any products", style = TextStyle(
                    color = MaterialTheme.colors.onSurface.copy(0.6f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        Spacer(modifier = Modifier.padding(top = 6.dp))
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // Popular Products
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Most Popular Products",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
                ClickableText(
                    text = AnnotatedString("See all"),
                    style = TextStyle(
                        fontSize = 13.sp,
                        color = MaterialTheme.colors.onBackground
                    ),
                    onClick = { navController.navigate(NavigationScreens.MostPopularSeeAllScreen.route) }
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                if (homeViewModel.isPopularProductsLoading.value) {
                    items(5) {
                        ShoppingListItemShimmer()
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
                items(homeViewModel.mostPopularProducts) { product ->
                    ShoppingListItem(
                        product = product,
                        navController = navController,
                        productDetailViewModel = productDetailViewModel,
                        bookmarksViewModel = bookmarksViewModel,
                        userViewModel = userViewModel,
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            // Discounts
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Discounts",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )

                ClickableText(
                    text = AnnotatedString("See all"),
                    style = TextStyle(
                        fontSize = 13.sp,
                        color = MaterialTheme.colors.onBackground
                    ),
                    onClick = { navController.navigate(NavigationScreens.DiscountsSeeAllScreen.route) }
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                if (homeViewModel.isDiscountsProductsLoading.value) {
                    items(5) {
                        ShoppingListItemShimmer()
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
                items(homeViewModel.discountsProducts) { product ->
                    ShoppingListItem(
                        product = product,
                        navController,
                        productDetailViewModel = productDetailViewModel,
                        bookmarksViewModel = bookmarksViewModel,
                        userViewModel = userViewModel,
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            // New Products
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "New",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )

                ClickableText(
                    text = AnnotatedString("See all"),
                    style = TextStyle(
                        fontSize = 13.sp,
                        color = MaterialTheme.colors.onBackground
                    ),
                    onClick = { navController.navigate(NavigationScreens.NewSeeAllScreen.route) }
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                if (homeViewModel.isNewProductsLoading.value) {
                    items(5) {
                        ShoppingListItemShimmer()
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
                items(homeViewModel.newProducts) { product ->
                    ShoppingListItem(
                        product = product,
                        navController,
                        productDetailViewModel = productDetailViewModel,
                        bookmarksViewModel = bookmarksViewModel,
                        userViewModel = userViewModel,
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}


@ExperimentalTime
@ExperimentalPagerApi
@Composable
private fun Pager() {
    val pagerState = rememberPagerState(pageCount = 3, initialOffscreenLimit = 2)

    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth(),
        ) { page ->
            Card(
                Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(185.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { }
                ) {
                    // I'm just showing fake data
                    Shape()
                }
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            indicatorShape = RoundedCornerShape(50.dp),
            activeColor = MaterialTheme.colors.primary
        )
    }
}

@Preview
@Composable
private fun Shape() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .fillMaxHeight()
                .clip(CutCornerShape(bottomEnd = 500f))
                .background(MaterialTheme.colors.primary)
        )
        Image(
            painter = painterResource(
                id = R.drawable.shoe_pager_pic
            ),
            contentDescription = "",
            modifier = Modifier.padding(
                top = 3.dp,
                start = 12.dp
            )
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            Text(
                text = "-70% Off",
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(
                        bottom = 10.dp,
                        end = 18.dp
                    ),
                fontSize = 23.sp
            )

            Text(
                text = "Nike Joyride, Nike IN",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(
                        top = 23.dp,
                        end = 18.dp
                    )
            )
        }
    }
}
