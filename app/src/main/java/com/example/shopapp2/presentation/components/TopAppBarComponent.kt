package com.example.shopapp2.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shopapp2.presentation.theme.Purple500
import com.example.shopapp2.presentation.theme.customFont
import com.example.shopapp2.presentation.util.NavigationScreens

@Composable
fun TopAppBarComponent(navController: NavController) {
    val appBarColor = if (MaterialTheme.colors.isLight) {
        MaterialTheme.colors.surface
    } else {
        MaterialTheme.colors.primarySurface
    }

    TopAppBar(
        title = {
            Text(
                text = "Shop",
                modifier = Modifier.padding(start = 20.dp),
                style = TextStyle(
                    color = Purple500,
                    fontFamily = customFont,
                    letterSpacing = 1.sp,
                    fontSize = 20.sp
                )
            )
        },
        backgroundColor = appBarColor,
        actions = {
            IconButton(onClick = {
                navController.navigate(NavigationScreens.SearchScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    modifier = Modifier.padding(end = 16.dp),
                )
            }
        },
        elevation = 1.dp
    )
}

const val EXPLORE_WATCHES = 0
const val EXPLORE_CLOTHES = 1
const val EXPLORE_SHOES = 2

const val POPULAR_PRODUCTS = 3
const val DISCOUNTS = 4
const val NEW_PRODUCTS = 5
const val SEARCH = 6

const val CART = 7
const val ORDERS = 8

@Composable
fun StandardAppBar(
    navController: NavController,
    int: Int
) {
    val appBarColor = if (MaterialTheme.colors.isLight) {
        if (int == SEARCH || int == ORDERS) {
            MaterialTheme.colors.primary
        } else {
            MaterialTheme.colors.surface
        }
    } else {
        MaterialTheme.colors.primarySurface
    }

    var text = ""

    when (int) {
        EXPLORE_CLOTHES -> {
            text = "Clothes"
        }
        EXPLORE_SHOES -> {
            text = "Shoes"
        }
        EXPLORE_WATCHES -> {
            text = "Watches"
        }
        POPULAR_PRODUCTS -> {
            text = "Most popular products"
        }
        DISCOUNTS -> {
            text = "Discounts"
        }
        NEW_PRODUCTS -> {
            text = "New products"
        }
        SEARCH -> {
            text = "Search"
        }
        CART -> {
            text = "My Cart"
        }
        ORDERS -> {
            text = "My Orders"
        }
    }

    TopAppBar(
        title = {
            Text(
                text = text,
                fontWeight = FontWeight.Bold
            )
        },
        backgroundColor = appBarColor,
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIos,
                    contentDescription = "Back",
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        },
        elevation = 1.dp
    )
}