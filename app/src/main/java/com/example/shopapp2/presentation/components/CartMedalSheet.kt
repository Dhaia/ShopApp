package com.example.shopapp2.presentation.components

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.shopapp2.domain.models.CartProduct
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.cart.MyCartViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun AddToCartMedalSheetContent(
    product: Product,
    state: ModalBottomSheetState,
    scope: CoroutineScope,
    myCartViewModel: MyCartViewModel,
    userViewModel: UserViewModel
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            "Choose your size and color to continue",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(10.dp))

        // The product item
        ProductPreview(product, state, scope)

        Spacer(modifier = Modifier.padding(10.dp))

        val cartProduct = CartProduct(
            productId = product.id,
            productTitle = product.title,
            productPrice = product.price.toInt(),
            productImagesUrls = product.imagesUrls,
            productDescription = product.description,
            productSizes = product.sizes,
            productColors = product.colors,
            productDiscount = product.discount,
        )

        // The size
        Row {
            Text("Choose the size", fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.padding(10.dp))

            val (expanded, setExpanded) = remember { mutableStateOf(false) }
            val (selectedIndex, setSelectedIndex) = remember { mutableStateOf(0) }
            DropDownMenuComponent(
                items = cartProduct.productSizes as List<String>,
                expanded = expanded,
                setExpanded = setExpanded,
                selectedIndex = selectedIndex,
                setSelectedIndex = setSelectedIndex,
                modifier = Modifier
            )

            if (cartProduct.productSizes.isNotEmpty()) {
                cartProduct.size = cartProduct.productSizes[selectedIndex].toString()
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))

        Row {
            Text("Choose the Color", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.padding(5.dp))

            val (expanded, setExpanded) = remember { mutableStateOf(false) }
            val (selectedIndex, setSelectedIndex) = remember { mutableStateOf(0) }
            DropDownMenuComponent(
                items = cartProduct.productColors as List<String>,
                expanded = expanded,
                setExpanded = setExpanded,
                selectedIndex = selectedIndex,
                setSelectedIndex = setSelectedIndex,
                modifier = Modifier
            )

            if (cartProduct.productSizes.isNotEmpty()) {
                cartProduct.color = cartProduct.productColors[selectedIndex].toString()
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {

                val doesProductAlreadyExist =
                    userViewModel.user.value.cartProducts.find { it.productId == product.id }
                if (doesProductAlreadyExist == null) {
                    myCartViewModel.addCartProduct(cartProduct = cartProduct, userViewModel)
                    userViewModel.updateUserData()
                    scope.launch { state.hide() }
                } else {
                    Toast.makeText(context, "This item is already in your cart!", Toast.LENGTH_LONG)
                        .show()
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(20.dp))
        ) {
            Text("Add to cart")
        }

    }
}

@ExperimentalMaterialApi
@Composable
private fun ProductPreview(
    product: Product,
    state: ModalBottomSheetState,
    scope: CoroutineScope
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            if (product.imagesUrls.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(Uri.parse(product.imagesUrls[0])),
                    contentDescription = "Product image",
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.3f)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                if (state.isVisible) {
                    scope.launch { state.hide() }
                }
            }
            Column(
                Modifier
                    .padding(start = 8.dp, top = 3.dp, bottom = 3.dp)
            ) {

                // Title
                Text(
                    text = product.title,
                    fontSize = 17.sp,
                    maxLines = 2,
                )
                Spacer(modifier = Modifier.padding(4.dp))

                // Price
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    if (product.discountBoolean) {
                        Text(
                            text = "$${product.price}",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colors.primary.copy(0.8f),
                            style = TextStyle(
                                textDecoration = TextDecoration.LineThrough
                            )
                        )
                        Spacer(modifier = Modifier.padding(3.dp))
                        Text(
                            text = "$${product.discount}",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colors.primary
                        )
                    } else {
                        Text(
                            text = "$${product.price}",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }
    }
}