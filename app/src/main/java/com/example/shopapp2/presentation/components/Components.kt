package com.example.shopapp2.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DropDownMenuComponent(
    modifier: Modifier,
    expanded: Boolean,
    setExpanded: (Boolean) -> Unit,
    items: List<String>,
    selectedIndex: Int,
    setSelectedIndex: (Int) -> Unit,
) {
    Box(
        modifier = modifier
    )
    {
        Box(
            modifier = Modifier
                .clickable(onClick = { setExpanded(true) })
        )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(items.isNotEmpty()){
                    Text(
                        items[selectedIndex],
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.padding(2.dp))
                Icon(Icons.Filled.Sort, contentDescription = "")
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { setExpanded(false) },
            modifier = Modifier
                .background(
                    MaterialTheme.colors.surface.copy(0.7f)
                )
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    setSelectedIndex(index)
                    setExpanded(false)
                }) {
                    Text(text = text, color = MaterialTheme.colors.primary)
                }
            }
        }
    }
}