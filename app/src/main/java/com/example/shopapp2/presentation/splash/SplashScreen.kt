package com.example.shopapp2.presentation.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp2.presentation.theme.Purple700
import com.example.shopapp2.presentation.util.NavigationScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(
    navController: NavController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {

    val scale = remember {
        Animatable(0f)
    }
    val overshootInterpolator = remember {
        OvershootInterpolator(2f)
    }
    LaunchedEffect(key1 = true) {

        withContext(Dispatchers.Main) {
            scale.animateTo(
                targetValue = 0.7f,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = {
                        overshootInterpolator.getInterpolation(it)
                    }
                )
            )
            val isSignedIn = splashViewModel.isSignedIn
            delay(500)
            navController.popBackStack()
            if (isSignedIn.value) {
                navController.navigate(NavigationScreens.HomeScreen.route)
            } else {
                navController.navigate(NavigationScreens.LoginScreen.route)
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize().background(Purple700),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Default.Shop,
            contentDescription = "",
            modifier = Modifier
                .size(150.dp)
                .scale(scale.value),
            tint = Color.White
        )
    }
}