package com.example.shopapp2.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationScreens(var route: String, var icon: ImageVector?, var title: String) {

    object SplashScreen : NavigationScreens("SplashScreen", null, "Splash Screen")
    object SearchScreen : NavigationScreens("SearchScreen", null, "SearchScreen")

    object RegisterScreen : NavigationScreens("register", null, "Home")
    object LoginScreen : NavigationScreens("login", null, "Explore")
    object PasswordResetScreen : NavigationScreens("PasswordReset", null, "Password Reset")
    object EmailSentScreen : NavigationScreens("Email Sent", null, "Email Sent")
    object MyCartScreen : NavigationScreens("MyCart", null, "My Cart")

    object ProductDetailScreen : NavigationScreens("ProductDetail", null, "Product Detail")
    object HomeScreen : NavigationScreens("home", Icons.Filled.Home, "Home")
    object ExploreScreen : NavigationScreens("explore", Icons.Outlined.Explore, "Explore")
    object BookmarksScreen : NavigationScreens("bookmarks", Icons.Filled.Bookmarks, "Bookmarks")
    object ProfileScreen : NavigationScreens("profile", Icons.Filled.PermIdentity, "Profile")
    object MyOrdersScreen : NavigationScreens("MyOrdersScreen", null, "My Orders")

    object MostPopularSeeAllScreen :
        NavigationScreens("MostPopularSeeAllScreen", null, "MostPopularSeeAllScreen")

    object DiscountsSeeAllScreen :
        NavigationScreens("DiscountsSeeAllScreen", null, "DiscountsSeeAllScreen")

    object NewSeeAllScreen : NavigationScreens("NewSeeAllScreen", null, "NewSeeAllScreen")

    object ShoesScreen : NavigationScreens("ShoesScreen", null, "ShoesScreen")
    object WatchesScreen : NavigationScreens("WatchesScreen", null, "WatchesScreen")
    object ClothesScreen : NavigationScreens("ClothesScreen", null, "ClothesScreen")

    object StepOneScreen : NavigationScreens("StepOneScreen", null, "My Orders")
    object StepTwoScreen : NavigationScreens("StepTwoScreen", null, "My Orders")
    object OrderCompletedScreen :
        NavigationScreens("OrderCompletedScreen", null, "OrderCompletedScreen")

}
