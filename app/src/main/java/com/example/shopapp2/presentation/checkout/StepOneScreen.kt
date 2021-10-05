package com.example.shopapp2.presentation.checkout

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.shopapp2.domain.models.CartProduct
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.theme.Purple500
import com.example.shopapp2.presentation.util.NavigationScreens

@Composable
fun StepOneScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Confirm your orders", fontWeight = FontWeight.Bold)
            ClickableText(
                text = AnnotatedString("Cancel"),
                onClick = {
                    navController.popBackStack()
                    navController.navigateUp()
                },
                style = TextStyle(
                    fontSize = 13.sp,
                    color = MaterialTheme.colors.error,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline
                ),
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = "Make sure everything is correct before proceeding",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colors.onSurface.copy(0.65f)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        LazyColumn {
            items(userViewModel.user.value.cartProducts) { product ->
                ConfirmOrderListItem(product = product)
                Spacer(modifier = Modifier.padding(5.dp))
            }
        }
    }
}

@Composable
fun StepOneScreenBottomBar(
    navController: NavController,
    userViewModel: UserViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterHorizontally),
            thickness = 2.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            var totalPrice = 0

            userViewModel.user.value.cartProducts.forEachIndexed { index, cartProduct ->
                totalPrice += cartProduct.productPrice
            }

            Text(text = "Total: $$totalPrice")
            Text(text = "${userViewModel.user.value.cartProducts.size} items")
        }

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
                Column {
                    Text(text = "Step 1 our of 2", fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(
                        text = "Confirm your orders", fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp, color = MaterialTheme.colors.onSurface.copy(0.65f)
                    )
                }

                Button(
                    onClick = {
                        navController.popBackStack()
                        navController.navigate(NavigationScreens.StepTwoScreen.route)
                    },
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Next")
                }
            }
        }
    }
}

@Composable
private fun ConfirmOrderListItem(
    product: CartProduct,
) {

    Surface(
        shape = RoundedCornerShape(20.dp),
        elevation = 3.dp,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colors.surface)
                .clickable {}
        ) {
            Spacer(modifier = Modifier.padding(10.dp))

            Row(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = rememberImagePainter(Uri.parse(product.productImagesUrls[0])),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.4f)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, top = 3.dp, bottom = 3.dp, end = 4.dp)
                ) {

                    // Title
                    Text(
                        text = product.productTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 1,
                    )
                    Spacer(modifier = Modifier.padding(6.dp))

                    Row {
                        // Size
                        Text(
                            text = "Size: ",
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.onSurface.copy(0.45f),
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = product.size,
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.onSurface,
                            maxLines = 1,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                    Spacer(modifier = Modifier.padding(3.dp))

                    Row {
                        Text(
                            text = "Color: ",
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.onSurface.copy(0.45f),
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = product.color,
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.onSurface,
                            maxLines = 1,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 5.dp),
                        text = product.productPrice.toString(),
                        fontSize = 14.sp,
                        color = Purple500,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}
