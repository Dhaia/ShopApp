package com.example.shopapp2.presentation.components


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shopapp2.presentation.util.NavigationScreens

@Composable
fun FloatingActionButtonComponent(navController: NavController) {

    val actionBarColor = MaterialTheme.colors.primary

    FloatingActionButton(
        onClick = { navController.navigate(NavigationScreens.MyCartScreen.route) },
        shape = RoundedCornerShape(50),
        backgroundColor = actionBarColor,
        modifier = Modifier.padding(bottom = 6.dp)
    ) {
        Icon(
            Icons.Filled.ShoppingCart,
            "",
        )
    }
}