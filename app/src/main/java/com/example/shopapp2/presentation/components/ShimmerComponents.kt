package com.example.shopapp2.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp2.R
import com.example.shopapp2.presentation.theme.Purple500
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder


@Composable
fun CartListItemShimmer() {

    val shimmerColor = MaterialTheme.colors.primary

    Box(
        modifier = Modifier
            .fillMaxWidth()
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
            ) {
                Spacer(modifier = Modifier.padding(10.dp))

                Row(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.4f)
                            .placeholder(
                                visible = true,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = shimmerColor,
                                shape = RoundedCornerShape(20.dp)
                            ),
                    )
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(start = 8.dp, top = 3.dp, bottom = 3.dp, end = 4.dp)
                    ) {

                        // Title
                        Text(
                            text = "title",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            maxLines = 1,
                            modifier = Modifier.placeholder(
                                visible = true,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = shimmerColor,
                                shape = RoundedCornerShape(5.dp)
                            ),
                        )
                        Spacer(modifier = Modifier.padding(6.dp))

                        Row {
                            // Size
                            Text(
                                text = "size",
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.onSurface,
                                maxLines = 1,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.placeholder(
                                    visible = true,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = shimmerColor,
                                    shape = RoundedCornerShape(5.dp)
                                ),
                            )
                        }

                        Spacer(modifier = Modifier.padding(3.dp))

                        Row {
                            Text(
                                text = "color",
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.onSurface,
                                maxLines = 1,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.placeholder(
                                    visible = true,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = shimmerColor,
                                    shape = RoundedCornerShape(5.dp)
                                ),
                            )
                        }
                        Spacer(modifier = Modifier.padding(5.dp))

                        // Price
                        Text(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(end = 5.dp)
                                .placeholder(
                                    visible = true,
                                    highlight = PlaceholderHighlight.shimmer(),
                                    color = shimmerColor,
                                    shape = RoundedCornerShape(5.dp)
                                ),
                            text = "price",
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
}

@Composable
fun BookmarksListItemShimmer() {

    val shimmerColor = MaterialTheme.colors.primary

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.4f)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = shimmerColor,
                        shape = RoundedCornerShape(20.dp)
                    ),
            )

            Column(
                Modifier
                    //.fillMaxSize()
                    .padding(start = 8.dp, top = 3.dp, bottom = 3.dp)
            ) {

                Text(
                    text = "title",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    modifier = Modifier.placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = shimmerColor,
                        shape = RoundedCornerShape(5.dp)
                    ),
                )
                Spacer(modifier = Modifier.padding(2.dp))

                Text(
                    text = "description of the product in two lines",
                    fontSize = 13.sp,
                    color = MaterialTheme.colors.onSurface.copy(0.7f),
                    maxLines = 2,
                    modifier = Modifier.placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = shimmerColor,
                        shape = RoundedCornerShape(5.dp)
                    ),
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "price",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Purple500,
                        modifier = Modifier.placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = shimmerColor,
                            shape = RoundedCornerShape(5.dp)
                        ),
                    )
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .placeholder(
                                visible = true,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = shimmerColor,
                                shape = RoundedCornerShape(20.dp)
                            ),
                    ) {
                        Text(
                            text = "Add to cart",
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShoppingListItemShimmer() {

    val shimmerColor = MaterialTheme.colors.primary

    Box(
        modifier = Modifier
            .width(150.dp)
            //.height(250.dp)

            .clip(RoundedCornerShape(15.dp))
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(170.dp)
                    .fillMaxWidth()
                    .placeholder(
                        visible = true,
                        color = shimmerColor,
                        highlight = PlaceholderHighlight.shimmer(),
                        shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp)
                    ),
            )

            Spacer(modifier = Modifier.padding(4.dp))

            // Title
            Text(
                text = "title",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterHorizontally)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = shimmerColor,
                        shape = RoundedCornerShape(15.dp)
                    ),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 2
            )
        }
    }
}