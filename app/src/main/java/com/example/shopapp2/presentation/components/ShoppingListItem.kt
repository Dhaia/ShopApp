package com.example.shopapp2.presentation.components

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.BookmarkBorder
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
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.shopapp2.domain.models.Product
import com.example.shopapp2.presentation.UserViewModel
import com.example.shopapp2.presentation.bookmarks.BookmarksViewModel
import com.example.shopapp2.presentation.product_detail.ProductDetailViewModel
import com.example.shopapp2.presentation.util.NavigationScreens
import timber.log.Timber

@Composable
fun ShoppingListItem(
    product: Product,
    navController: NavController,
    productDetailViewModel: ProductDetailViewModel,
    bookmarksViewModel: BookmarksViewModel,
    userViewModel: UserViewModel,
) {
    val context = LocalContext.current
    Surface(
        elevation = 1.dp,
        shape = RoundedCornerShape(15.dp)
    ) {

        Box(modifier = Modifier
            .width(150.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colors.surface)
            .clickable {
                productDetailViewModel.setProduct(product)
                navController.navigate(
                    NavigationScreens.ProductDetailScreen.route
                )
            }
        ) {
            Column {
                if (product.imagesUrls.isNotEmpty()) {
                    Image(
                        painter = rememberImagePainter(Uri.parse(product.imagesUrls[0])),
                        modifier = Modifier
                            .height(170.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp)),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )
                }
                Spacer(modifier = Modifier.padding(4.dp))

                // Title
                Text(
                    text = product.title,
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2
                )

                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    // Price
                    if (!product.discountBoolean) {
                        Text(
                            text = "$${product.price}",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.primary,
                        )
                    } else {

                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "$${product.price}",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 10.sp,
                                color = MaterialTheme.colors.primary,
                                style = TextStyle(
                                    textDecoration = TextDecoration.LineThrough
                                ),
                            )
                            Spacer(modifier = Modifier.padding(2.dp))
                            Text(
                                text = "$${product.discount}",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.primary,
                            )
                        }
                    }

                    val tint = remember {
                        mutableStateOf(false)
                    }
                    val isBookmarked =
                        userViewModel.user.value.bookmarksProducts.find { it.id == product.id }
                    Timber.d("hmm isBookmarked = $isBookmarked")
                    tint.value = isBookmarked != null


                    val context = LocalContext.current
                    Icon(
                        if (tint.value)
                            Icons.Filled.BookmarkAdded
                        else
                            Icons.Filled.BookmarkBorder,
                        contentDescription = "Add bookmark",
                        Modifier
                            .align(Alignment.CenterVertically)
                            .clickable {
                                if (tint.value) {

                                    // Delete the bookmark
                                    if (isBookmarked != null) {
                                        Timber.d("In ShoppingListItem calling delete $isBookmarked")
                                        bookmarksViewModel.deleteBookmark(isBookmarked)

                                        userViewModel.updateUserData()
                                    }
                                } else {
                                    addBookmark(
                                        product, bookmarksViewModel, userViewModel,
                                        context
                                    )
                                }
                            },
                        tint =
                        if (tint.value)
                            MaterialTheme.colors.primary
                        else
                            MaterialTheme.colors.onSurface.copy(0.4f)
                    )
                }
            }
        }
    }

}


fun addBookmark(
    product: Product,
    bookmarksViewModel: BookmarksViewModel,
    userViewModel: UserViewModel,
    context: Context,
) {

    val isBookmarkProductExists =
        userViewModel.user.value.bookmarksProducts.find { it.id == product.id }
    if (isBookmarkProductExists == null) {

        bookmarksViewModel.addBookmark(product, userViewModel)
        userViewModel.updateUserData()
    } else {
        Toast.makeText(context, "This item is already in your bookmarks list", Toast.LENGTH_LONG)
            .show()
    }
}
