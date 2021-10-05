package com.example.shopapp2.presentation.util

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.bookmarks.BookmarksScreen
import com.example.shopapp2.presentation.bookmarks.BookmarksViewModel
import com.example.shopapp2.presentation.cart.MyCartScreen
import com.example.shopapp2.presentation.cart.MyCartViewModel
import com.example.shopapp2.presentation.checkout.OrderCompletedScreen
import com.example.shopapp2.presentation.checkout.OrdersViewModel
import com.example.shopapp2.presentation.checkout.StepOneScreen
import com.example.shopapp2.presentation.checkout.StepTwoScreen
import com.example.shopapp2.presentation.explore.ExploreScreen
import com.example.shopapp2.presentation.explore.clothes.ClothesScreen
import com.example.shopapp2.presentation.explore.clothes.ClothesViewModel
import com.example.shopapp2.presentation.explore.shoes.ShoesScreen
import com.example.shopapp2.presentation.explore.shoes.ShoesViewModel
import com.example.shopapp2.presentation.explore.watches.WatchesScreen
import com.example.shopapp2.presentation.explore.watches.WatchesViewModel
import com.example.shopapp2.presentation.home.HomeScreen
import com.example.shopapp2.presentation.home.HomeViewModel
import com.example.shopapp2.presentation.home.see_all.discounts.DiscountsSeeAllScreen
import com.example.shopapp2.presentation.home.see_all.most_popular.MostPopularSeeAllScreen
import com.example.shopapp2.presentation.home.see_all.new.NewSeeAllScreen
import com.example.shopapp2.presentation.login.login.LoginScreen
import com.example.shopapp2.presentation.login.password_reset.EmailSentScreen
import com.example.shopapp2.presentation.login.password_reset.PasswordResetScreen
import com.example.shopapp2.presentation.login.register.RegisterScreen
import com.example.shopapp2.presentation.product_detail.ProductDetailScreen
import com.example.shopapp2.presentation.product_detail.ProductDetailViewModel
import com.example.shopapp2.presentation.profile.ProfileScreen
import com.example.shopapp2.presentation.profile.ProfileViewModel
import com.example.shopapp2.presentation.profile.orders.MyOrdersScreen
import com.example.shopapp2.presentation.search.SearchScreen
import com.example.shopapp2.presentation.splash.SplashScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlin.time.ExperimentalTime

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalTime
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier,
    productDetailViewModel: ProductDetailViewModel,
    bookmarksViewModel: BookmarksViewModel,
    myCartViewModel: MyCartViewModel,
    homeViewModel: HomeViewModel,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel,
    ordersViewModel: OrdersViewModel,
    clothesViewModel: ClothesViewModel,
    watchesViewModel: WatchesViewModel,
    shoesViewModel: ShoesViewModel,
) {

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(
            NavigationScreens.LoginScreen.route,
        ) {
            LoginScreen(
                navController,
                userViewModel = userViewModel,
                homeViewModel = homeViewModel,
                watchesViewModel = watchesViewModel,
                clothesViewModel = clothesViewModel,
                shoesViewModel = shoesViewModel
            )
        }
        composable(
            NavigationScreens.RegisterScreen.route,
        ) {
            RegisterScreen(
                navController,
                userViewModel = userViewModel,
                homeViewModel = homeViewModel,
                watchesViewModel = watchesViewModel,
                clothesViewModel = clothesViewModel,
                shoesViewModel = shoesViewModel
            )
        }
        composable(
            NavigationScreens.PasswordResetScreen.route,
        )
        {
            PasswordResetScreen(navController)
        }
        composable(NavigationScreens.EmailSentScreen.route) {
            EmailSentScreen(navController)
        }
        composable(NavigationScreens.MyOrdersScreen.route) {
            MyOrdersScreen()
        }
        composable(NavigationScreens.SearchScreen.route) {
            SearchScreen(
                navController = navController,
                userViewModel = userViewModel,
                productDetailViewModel = productDetailViewModel,
                myCartViewModel = myCartViewModel
            )
        }

        composable(
            NavigationScreens.HomeScreen.route,
            exitTransition = { _, _ ->
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = { _, _ ->
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(animationSpec = tween(300))
            },
        ) {
            HomeScreen(
                navController = navController,
                productDetailViewModel = productDetailViewModel,
                bookmarksViewModel = bookmarksViewModel,
                homeViewModel = homeViewModel,
                userViewModel = userViewModel
            )
        }
        composable(
            NavigationScreens.ProductDetailScreen.route,
            enterTransition = { _, _ ->
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = { _, _ ->
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(animationSpec = tween(300))
            }) {
            ProductDetailScreen(
                productDetailViewModel = productDetailViewModel,
                myCartViewModel = myCartViewModel,
                userViewModel = userViewModel,
                bookmarksViewModel = bookmarksViewModel
            )
        }

        composable(NavigationScreens.MyCartScreen.route) {
            MyCartScreen(
                myCartViewModel = myCartViewModel,
                userViewModel = userViewModel,
            )
        }
        composable(NavigationScreens.ExploreScreen.route) {
            ExploreScreen(navController = navController)
        }
        composable(NavigationScreens.BookmarksScreen.route) {
            BookmarksScreen(
                navController = navController,
                bookmarksViewModel = bookmarksViewModel,
                myCartViewModel = myCartViewModel,
                userViewModel = userViewModel,
                productDetailViewModel = productDetailViewModel
            )
        }
        composable(NavigationScreens.ProfileScreen.route) {
            ProfileScreen(
                navController = navController,
                userViewModel = userViewModel,
                profileViewModel = profileViewModel
            )
        }

        composable(NavigationScreens.StepOneScreen.route) {
            StepOneScreen(navController = navController, userViewModel)
        }
        composable(NavigationScreens.StepTwoScreen.route) {
            StepTwoScreen(
                navController = navController,
                userViewModel = userViewModel,
                ordersViewModel = ordersViewModel
            )
        }
        composable(NavigationScreens.OrderCompletedScreen.route) {
            OrderCompletedScreen(navController = navController)
        }
        composable(NavigationScreens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(NavigationScreens.MostPopularSeeAllScreen.route) {
            MostPopularSeeAllScreen(
                homeViewModel = homeViewModel,
                navController = navController,
                productDetailViewModel = productDetailViewModel,
                userViewModel = userViewModel,
                myCartViewModel = myCartViewModel,
            )
        }
        composable(NavigationScreens.DiscountsSeeAllScreen.route) {
            DiscountsSeeAllScreen(
                homeViewModel = homeViewModel,
                navController = navController,
                productDetailViewModel = productDetailViewModel,
                userViewModel = userViewModel,
                myCartViewModel = myCartViewModel,
            )
        }
        composable(NavigationScreens.NewSeeAllScreen.route) {
            NewSeeAllScreen(
                homeViewModel = homeViewModel,
                navController = navController,
                productDetailViewModel = productDetailViewModel,
                userViewModel = userViewModel,
                myCartViewModel = myCartViewModel,
            )
        }

        composable(NavigationScreens.ClothesScreen.route) {
            ClothesScreen(
                clothesViewModel,
                navController = navController,
                productDetailViewModel = productDetailViewModel,
                userViewModel = userViewModel,
                myCartViewModel = myCartViewModel,
            )
        }
        composable(NavigationScreens.WatchesScreen.route) {
            WatchesScreen(
                watchesViewModel,
                navController = navController,
                productDetailViewModel = productDetailViewModel,
                userViewModel = userViewModel,
                myCartViewModel = myCartViewModel,
            )
        }
        composable(NavigationScreens.ShoesScreen.route) {
            ShoesScreen(
                shoesViewModel,
                navController = navController,
                productDetailViewModel = productDetailViewModel,
                userViewModel = userViewModel,
                myCartViewModel = myCartViewModel,
            )
        }
    }
}














