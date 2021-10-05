package com.example.shopapp2.presentation.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.shopapp2.domain.models.Order
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.util.NavigationScreens
import timber.log.Timber

@Composable
fun StepTwoScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    ordersViewModel: OrdersViewModel
) {
    if (ordersViewModel.isLoading.value) {
        Dialog(
            onDismissRequest = { },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            CircularProgressIndicator()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(text = "Confirm your personal information", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.padding(5.dp))

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
        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = "These information will be used to deliver your packages," +
                    " please make sure everything is correct",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colors.onSurface.copy(0.65f)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        ClickableText(
            text = AnnotatedString("Edit my personal information"),
            onClick = {
                navController.navigate(NavigationScreens.ProfileScreen.route)
            },
            style = TextStyle(
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colors.onBackground
            )
        )

        Spacer(modifier = Modifier.padding(10.dp))

        ShowContent(userViewModel)
    }
}

@Composable
fun ShowContent(userViewModel: UserViewModel) {

    // Name Text above Text field
    Text(
        modifier = Modifier
            .padding(bottom = 5.dp, start = 5.dp),
        text = "Name",
        style = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.onSurface.copy(0.75f)
        )
    )
    // Name Text Field
    TextField(
        value = userViewModel.user.value.name,
        onValueChange = {},
        enabled = false
    )
    Spacer(modifier = Modifier.padding(7.dp))

    // Phone Number Text above Text field
    Text(
        modifier = Modifier
            .padding(bottom = 5.dp, start = 5.dp),
        text = "Phone Number",
        style = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.onSurface.copy(0.75f)
        )
    )
    // Phone Number Text Field
    TextField(
        value = userViewModel.user.value.phoneNumber,
        onValueChange = {},
        enabled = false
    )
    Spacer(modifier = Modifier.padding(7.dp))


    // State Text above Text field
    Text(
        modifier = Modifier
            .padding(bottom = 5.dp, start = 5.dp),
        text = "State",
        style = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.onSurface.copy(0.75f)
        )
    )
    // State Text Field
    TextField(
        value = userViewModel.user.value.state,
        onValueChange = {},
        enabled = false
    )
    Spacer(modifier = Modifier.padding(7.dp))

    // District Text above Text field
    Text(
        modifier = Modifier
            .padding(bottom = 5.dp, start = 5.dp),
        text = "District",
        style = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.onSurface.copy(0.75f)
        )
    )
    // District Text Field
    TextField(
        value = userViewModel.user.value.district,
        onValueChange = {},
        enabled = false
    )
    Spacer(modifier = Modifier.padding(7.dp))
}

@Composable
fun StepTwoScreenBottomBar(
    navController: NavController,
    ordersViewModel: OrdersViewModel,
    userViewModel: UserViewModel
) {
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
            Column(modifier = Modifier.fillMaxWidth(0.65f)) {
                Text(text = "Step 2 our of 2", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.padding(5.dp))

                Text(
                    text = "Confirming your personal information", fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp, color = MaterialTheme.colors.onSurface.copy(0.65f)
                )
            }

            Button(
                onClick = {
                    addOrders(userViewModel, ordersViewModel, navController)
                },
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Finish")
            }
        }
    }

}

fun addOrders(
    userViewModel: UserViewModel,
    ordersViewModel: OrdersViewModel,
    navController: NavController
) {

    val myList: MutableList<Order> = mutableListOf<Order>()
    userViewModel.user.value.cartProducts.forEachIndexed { index, cartProduct ->
        val order = Order(
            productId = cartProduct.productId,
            productTitle = cartProduct.productTitle,
            productPrice = cartProduct.productPrice,
            productImagesUrls = cartProduct.productImagesUrls,
            productDescription = cartProduct.productDescription,
            productSizes = cartProduct.productSizes,
            productColors = cartProduct.productColors,
            productDiscount = cartProduct.productDiscount,

            orderSize = cartProduct.size,
            orderColor = cartProduct.color,
            orderQuantity = 1,
            orderPrice = cartProduct.productPrice,

            userId = userViewModel.user.value.id,
            userName = userViewModel.user.value.name,
            userEmail = userViewModel.user.value.email,
            userPhoneNumber = userViewModel.user.value.phoneNumber,
            userState = userViewModel.user.value.state,
            userDistrict = userViewModel.user.value.district,
        )
        myList.add(order)
    }
    ordersViewModel.addOrders(list = myList, navController = navController)

}
