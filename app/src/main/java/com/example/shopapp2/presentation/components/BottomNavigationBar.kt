package com.example.shopapp2.presentation.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.util.NavigationScreens

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val items = listOf(
        NavigationScreens.HomeScreen,
        NavigationScreens.ExploreScreen,
        NavigationScreens.BookmarksScreen,
        NavigationScreens.ProfileScreen,
    )
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val color = if (MaterialTheme.colors.isLight) {
        MaterialTheme.colors.surface
    } else {
        MaterialTheme.colors.primarySurface
    }

    BottomAppBar(
        backgroundColor = color,
        contentColor = MaterialTheme.colors.primary,
        cutoutShape = RoundedCornerShape(50),
    ) {
        items.forEach { item ->

            if (item == items[2]) {
                Box(modifier = Modifier.weight(1f))
            }

            BottomNavigationItem(modifier = Modifier.weight(1F),
                icon = {
                    item.icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = ""
                        )
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        //color = Color.Black,
                        fontSize = 7.5.sp, fontWeight = FontWeight.SemiBold
                    )
                },
                selectedContentColor = MaterialTheme.colors.onSurface,
                unselectedContentColor = MaterialTheme.colors.onSurface.copy(0.4f),
                alwaysShowLabel = false,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun MyCartBottomBar(
    navController: NavHostController,
    userViewModel: UserViewModel
) {

    val context = LocalContext.current

    // Checkout bottom bar
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            var totalPrice = 0

            userViewModel.user.value.cartProducts.forEachIndexed { index, cartProduct ->
                totalPrice += cartProduct.productPrice
            }

            Text(
                text = "$$totalPrice",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1,
            )

            Button(
                onClick = {
                    if (userViewModel.user.value.cartProducts.isNotEmpty()) {

                        if (userViewModel.user.value.name.isEmpty()
                            || userViewModel.user.value.phoneNumber.isEmpty()
                            || userViewModel.user.value.state.isEmpty()
                            || userViewModel.user.value.district.isEmpty()
                        ) {
                            Toast.makeText(
                                context, "Your personal information is empty, " +
                                        "Please update it in your profile page",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        } else {
                            navController.navigate(NavigationScreens.StepOneScreen.route)
                        }
                    }
                },
                modifier = Modifier.clip(RoundedCornerShape(13.dp))
            ) {
                Text(
                    text = "Checkout",
                )
            }
        }
    }
}
