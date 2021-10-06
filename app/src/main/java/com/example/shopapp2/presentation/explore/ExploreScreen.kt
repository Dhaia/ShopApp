package com.example.shopapp2.presentation.explore

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shopapp2.R
import com.example.shopapp2.presentation.theme.customFont
import com.example.shopapp2.presentation.theme.giloryFont
import com.example.shopapp2.presentation.util.NavigationScreens

@Composable
fun ExploreScreen(navController: NavController) {
    ShowContent(navController)
}

@Composable
private fun ShowContent(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = "Explore categories",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.padding(10.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
            .clickable {
                navController.navigate(NavigationScreens.WatchesScreen.route)
            }
        ) {
            Shape(
                imageId = R.drawable.explore_watches_pic,
                "Watches"
            )
        }

        Spacer(modifier = Modifier.padding(7.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
            .clickable {
                navController.navigate(NavigationScreens.ClothesScreen.route)
            }
        ) {
            Shape(
                imageId = R.drawable.explore_clothes_pic,
                "Clothes"
            )
        }
        Spacer(modifier = Modifier.padding(7.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
            .clickable {
                navController.navigate(NavigationScreens.ShoesScreen.route)
            }
        ) {
            Shape(
                imageId = R.drawable.explore_shoes_pic,
                "Shoes"
            )
        }

    }
}

@Composable
private fun Shape(imageId: Int, text: String) {

    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .shadow(35.dp)
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 50.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(150.dp)
                .clip(CutCornerShape(bottomEnd = 500f))
                .background(MaterialTheme.colors.primary)
                .padding(top = 18.dp, start = 10.dp)
        ) {
            Text(
                text = text, Modifier,
                color = MaterialTheme.colors.surface,
                fontSize = 25.sp,
                fontFamily = giloryFont,
                style = TextStyle(
                    letterSpacing = 0.5.sp,
                    color = Color.White.copy(0.8f),
                    shadow = Shadow(
                        color = if (isSystemInDarkTheme())
                            Color.Black.copy(0.1f)
                        else
                            Color.Black.copy(0.15f),
                        offset = Offset(5f, 14f),
                        blurRadius = 20f,
                    ), fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}
