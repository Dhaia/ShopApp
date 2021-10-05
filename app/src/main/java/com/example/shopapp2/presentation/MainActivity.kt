package com.example.shopapp2.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.algolia.search.client.ClientSearch
import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID
import com.algolia.search.model.IndexName
import com.example.shopapp2.common.FirebaseConstants
import com.example.shopapp2.presentation.bookmarks.BookmarksViewModel
import com.example.shopapp2.presentation.cart.MyCartViewModel
import com.example.shopapp2.presentation.checkout.OrdersViewModel
import com.example.shopapp2.presentation.checkout.StepOneScreenBottomBar
import com.example.shopapp2.presentation.checkout.StepTwoScreenBottomBar
import com.example.shopapp2.presentation.components.*
import com.example.shopapp2.presentation.explore.clothes.ClothesViewModel
import com.example.shopapp2.presentation.explore.shoes.ShoesViewModel
import com.example.shopapp2.presentation.explore.watches.WatchesViewModel
import com.example.shopapp2.presentation.home.HomeViewModel
import com.example.shopapp2.presentation.product_detail.ProductDetailViewModel
import com.example.shopapp2.presentation.profile.ProfileViewModel
import com.example.shopapp2.presentation.theme.ShopApp2Theme
import com.example.shopapp2.presentation.util.Navigation
import com.example.shopapp2.presentation.util.NavigationScreens
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val userViewModel by viewModels<UserViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()

    private val productDetailViewModel by viewModels<ProductDetailViewModel>()
    private val bookmarksViewModel by viewModels<BookmarksViewModel>()
    private val myCartViewModel by viewModels<MyCartViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val ordersViewModel by viewModels<OrdersViewModel>()

    private val clothesViewModel by viewModels<ClothesViewModel>()
    private val watchesViewModel by viewModels<WatchesViewModel>()
    private val shoesViewModel by viewModels<ShoesViewModel>()

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    @ExperimentalPagerApi
    @ExperimentalTime
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {

        Timber.plant(Timber.DebugTree())

        super.onCreate(savedInstanceState)
        setContent {
            ShopApp2Theme {
                MainScreen(
                    productDetailViewModel, bookmarksViewModel,
                    myCartViewModel, homeViewModel, userViewModel, profileViewModel,
                    ordersViewModel, clothesViewModel, watchesViewModel, shoesViewModel
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalTime
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun MainScreen(
    productDetailViewModel: ProductDetailViewModel,
    bookmarksViewModel: BookmarksViewModel,
    myCartViewModel: MyCartViewModel,
    homeViewModel: HomeViewModel,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel,
    ordersViewModel: OrdersViewModel,
    clothesViewModel: ClothesViewModel,
    watchesViewModel: WatchesViewModel,
    shoesViewModel: ShoesViewModel
) {
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination: NavDestination? = navBackStackEntry?.destination

    val startDestination = NavigationScreens.SplashScreen.route

    val hideComponents = currentDestination?.route == NavigationScreens.LoginScreen.route
            || currentDestination?.route == NavigationScreens.PasswordResetScreen.route
            || currentDestination?.route == NavigationScreens.RegisterScreen.route
            || currentDestination?.route == NavigationScreens.MyCartScreen.route
            || currentDestination?.route == NavigationScreens.ProductDetailScreen.route
            || currentDestination?.route == NavigationScreens.MyOrdersScreen.route
            || currentDestination?.route == NavigationScreens.StepOneScreen.route
            || currentDestination?.route == NavigationScreens.StepTwoScreen.route
            || currentDestination?.route == NavigationScreens.OrderCompletedScreen.route
            || currentDestination?.route == NavigationScreens.SplashScreen.route
            || currentDestination?.route == NavigationScreens.WatchesScreen.route
            || currentDestination?.route == NavigationScreens.ClothesScreen.route
            || currentDestination?.route == NavigationScreens.ShoesScreen.route
            || currentDestination?.route == NavigationScreens.MostPopularSeeAllScreen.route
            || currentDestination?.route == NavigationScreens.NewSeeAllScreen.route
            || currentDestination?.route == NavigationScreens.DiscountsSeeAllScreen.route
            || currentDestination?.route == NavigationScreens.SearchScreen.route
            || currentDestination?.route == NavigationScreens.EmailSentScreen.route


    Scaffold(
        topBar = {
            if (!hideComponents) TopAppBarComponent(navController)
            if (currentDestination?.route == NavigationScreens.MyCartScreen.route)
                StandardAppBar(navController, CART)
            if (currentDestination?.route == NavigationScreens.MyOrdersScreen.route)
                StandardAppBar(navController, ORDERS)

            if (currentDestination?.route == NavigationScreens.MostPopularSeeAllScreen.route)
                StandardAppBar(navController, POPULAR_PRODUCTS)
            if (currentDestination?.route == NavigationScreens.DiscountsSeeAllScreen.route)
                StandardAppBar(navController, DISCOUNTS)
            if (currentDestination?.route == NavigationScreens.NewSeeAllScreen.route)
                StandardAppBar(navController, NEW_PRODUCTS)

            if (currentDestination?.route == NavigationScreens.WatchesScreen.route)
                StandardAppBar(navController, EXPLORE_WATCHES)
            if (currentDestination?.route == NavigationScreens.ShoesScreen.route)
                StandardAppBar(navController, EXPLORE_SHOES)
            if (currentDestination?.route == NavigationScreens.ClothesScreen.route)
                StandardAppBar(navController, EXPLORE_CLOTHES)

            if (currentDestination?.route == NavigationScreens.SearchScreen.route)
                StandardAppBar(navController, SEARCH)
        },
        floatingActionButton = {
            if (!hideComponents) FloatingActionButtonComponent(navController = navController)
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            if (!hideComponents)
                BottomNavigationBar(navController)
            if (currentDestination?.route == NavigationScreens.MyCartScreen.route)
                MyCartBottomBar(navController, userViewModel)
            if (currentDestination?.route == NavigationScreens.StepOneScreen.route)
                StepOneScreenBottomBar(navController, userViewModel)
            if (currentDestination?.route == NavigationScreens.StepTwoScreen.route)
                StepTwoScreenBottomBar(
                    navController,
                    userViewModel = userViewModel,
                    ordersViewModel = ordersViewModel
                )
        },
    ) { innerPadding ->
        Navigation(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding),
            productDetailViewModel = productDetailViewModel,
            bookmarksViewModel = bookmarksViewModel,
            myCartViewModel = myCartViewModel,
            homeViewModel = homeViewModel,
            userViewModel = userViewModel,
            profileViewModel = profileViewModel,
            ordersViewModel = ordersViewModel,
            clothesViewModel = clothesViewModel,
            watchesViewModel = watchesViewModel,
            shoesViewModel = shoesViewModel
        )
    }
}
