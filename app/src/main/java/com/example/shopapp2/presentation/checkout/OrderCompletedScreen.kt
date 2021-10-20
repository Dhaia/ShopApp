package com.example.shopapp2.presentation.checkout

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shopapp2.R
import com.example.shopapp2.presentation.util.NavigationScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun OrderCompletedScreen(navController: NavController) {

    val scale = remember {
        Animatable(0f)
    }
    val overshootInterpolator = remember {
        OvershootInterpolator(2f)
    }
    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.Main) {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = {
                        overshootInterpolator.getInterpolation(it)
                    }
                )
            )
        }
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Spacer(modifier = Modifier.padding(30.dp))

            Icon(
                painterResource(R.drawable.done),
                contentDescription = "",
                modifier = Modifier
                    .size(200.dp)
                    .scale(scale.value),
                tint = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.padding(20.dp))
            Text("Your order is compete!", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(NavigationScreens.HomeScreen.route)
                }, shape = RoundedCornerShape(20.dp)
            ) {
                Text("Go back to home")
            }
        }
    }
}