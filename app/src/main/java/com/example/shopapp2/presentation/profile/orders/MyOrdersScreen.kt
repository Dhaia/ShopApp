package com.example.shopapp2.presentation.profile.orders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// TODO not yet implemented
@Composable
fun MyOrdersScreen(
) {

    val (tabIndex, setTabIndex) = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextTabs(tabIndex, setTabIndex)
        if (tabIndex == 0) {
            ActiveOrderScreen()
        } else (
                OldOrdersScreen()
                )

    }
}

@Composable
private fun OldOrdersScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Old Orders")
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
        }
    }
}

@Composable
private fun ActiveOrderScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Active Orders")
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {

        }
    }
}

@Composable
fun TextTabs(tabIndex: Int, setTabIndex: (Int) -> Unit) {
    val tabData = listOf(
        "Active orders",
        "Old orders",
    )
    TabRow(selectedTabIndex = tabIndex) {
        tabData.forEachIndexed { index, text ->
            Tab(selected = tabIndex == index,
                onClick = {
                    setTabIndex(index)
                },
                text = {
                    Text(text = text, fontWeight = FontWeight.SemiBold)
                })
        }
    }
}