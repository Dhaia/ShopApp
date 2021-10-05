package com.example.shopapp2.presentation.cart

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberImagePainter
import com.example.shopapp2.domain.models.CartProduct
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.components.CartListItemShimmer
import com.example.shopapp2.presentation.components.DropDownMenuComponent
import com.example.shopapp2.presentation.theme.Purple500
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe
import timber.log.Timber

@ExperimentalMaterialApi
@Composable
fun MyCartScreen(
    myCartViewModel: MyCartViewModel,
    userViewModel: UserViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {

            if (userViewModel.isLoading.value) {
                items(6) {
                    CartListItemShimmer()

                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
            items(userViewModel.user.value.cartProducts) { product ->

                RevealSwipe(
                    modifier = Modifier.padding(vertical = 5.dp),
                    directions = setOf(
                        RevealDirection.StartToEnd,
                        RevealDirection.EndToStart
                    ),
                    hiddenContentStart = {
                        Icon(
                            modifier = Modifier.padding(horizontal = 25.dp),
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                        )
                    },
                    hiddenContentEnd = {
                        Icon(
                            modifier = Modifier.padding(horizontal = 25.dp),
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    },
                    onBackgroundEndClick = {
                        myCartViewModel.deleteCartProduct(product)
                        userViewModel.updateUserData()
                    },
                    onBackgroundStartClick = {
                        myCartViewModel.deleteCartProduct(product)
                        userViewModel.updateUserData()
                    },
                    backgroundCardStartColor = MaterialTheme.colors.secondary
                ) {
                    CartListItem(
                        product, myCartViewModel = myCartViewModel,
                        userViewModel = userViewModel
                    )
                }
            }
        }
    }
}


@Composable
fun CartListItem(
    cartProduct: CartProduct,
    myCartViewModel: MyCartViewModel,
    userViewModel: UserViewModel
) {
    val isDialog = remember { mutableStateOf(false) }
    if (isDialog.value) {
        CartItemEditDialog(isDialog, cartProduct, userViewModel)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(178.dp)
    ) {

        Surface(
            shape = RoundedCornerShape(20.dp),
            elevation = 3.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
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

                    if (cartProduct.productImagesUrls.isNotEmpty()) {

                        Image(
                            painter = rememberImagePainter(Uri.parse(cartProduct.productImagesUrls[0])),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.4f)
                                .clip(RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(start = 8.dp, top = 3.dp, bottom = 3.dp, end = 4.dp)
                    ) {

                        // Title
                        Text(
                            text = cartProduct.productTitle,
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
                                text = cartProduct.size,
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.onSurface,
                                maxLines = 1,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }

                        Spacer(modifier = Modifier.padding(3.dp))

                        Row {
                            // Color
                            Text(
                                text = "Color: ",
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.onSurface.copy(0.45f),
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = cartProduct.color,
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.onSurface,
                                maxLines = 1,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                        Spacer(modifier = Modifier.padding(5.dp))

                        // Price
                        Text(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(end = 5.dp),
                            text = "$${cartProduct.productPrice.toString()}",
                            fontSize = 14.sp,
                            color = Purple500,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }

        Surface(
            shape = CircleShape,
            elevation = 7.dp,
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            IconButton(
                onClick = {
                    // Open a Dialog
                    isDialog.value = true
                },
                Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(MaterialTheme.colors.surface)
                    .align(Alignment.TopEnd)
            ) {
                Icon(Icons.Outlined.Edit, contentDescription = "")
            }
        }
    }
}

@Composable
private fun CartItemEditDialog(
    isDialog: MutableState<Boolean>,
    cartProduct: CartProduct,
    userViewModel: UserViewModel
) {
    Dialog(
        onDismissRequest = { isDialog.value = false },
        DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(20.dp)
                .clip(RoundedCornerShape(4.dp))
        ) {

            Text(
                text = "Edit your order",
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.padding(8.dp))

            val (colorsSelectedIndex, setColorsSelectedIndex) = remember { mutableStateOf(0) }
            val (sizesSelectedIndex, setSizesSelectedIndex) = remember { mutableStateOf(0) }

            Row {
                Text("Choose the size")

                Spacer(modifier = Modifier.padding(10.dp))

                val (expanded, setExpanded) = remember { mutableStateOf(false) }
                DropDownMenuComponent(
                    items = cartProduct.productSizes as List<String>,
                    expanded = expanded,
                    setExpanded = setExpanded,
                    selectedIndex = sizesSelectedIndex,
                    setSelectedIndex = setSizesSelectedIndex,
                    modifier = Modifier
                )
            }

            Spacer(modifier = Modifier.padding(4.dp))

            Row {
                Text("Choose the Color")
                Spacer(modifier = Modifier.padding(10.dp))

                val (expanded, setExpanded) = remember { mutableStateOf(false) }
                DropDownMenuComponent(
                    items = cartProduct.productColors as List<String>,
                    expanded = expanded,
                    setExpanded = setExpanded,
                    selectedIndex = colorsSelectedIndex,
                    setSelectedIndex = setColorsSelectedIndex,
                    modifier = Modifier
                )
            }

            Spacer(modifier = Modifier.padding(15.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {

                // Close button
                OutlinedButton(
                    onClick = { isDialog.value = false },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                ) {
                    Text(text = "Close")
                }
                Spacer(modifier = Modifier.padding(25.dp))
                // Save button
                Button(
                    onClick = {
                        if (
                            cartProduct.color == cartProduct.productColors[colorsSelectedIndex]
                            && cartProduct.size == cartProduct.productSizes[sizesSelectedIndex]
                        ) {
                            Timber.d("colorsSelectedIndex and sizesSelectedIndex same")
                        } else {

                            val newCartProduct = cartProduct
                            newCartProduct.color =
                                cartProduct.productColors[colorsSelectedIndex].toString()
                            newCartProduct.size =
                                cartProduct.productSizes[sizesSelectedIndex].toString()

                            val originalList = userViewModel.user.value.cartProducts.toMutableList()

                            originalList.remove(cartProduct)
                            originalList.add(newCartProduct)

                            val hashMap = hashMapOf<String, Any>(
                                "cartProducts" to originalList
                            )
                            userViewModel.updateUser(hashMap)
                            userViewModel.updateUserData()

                            isDialog.value = false
                        }
                    },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}












