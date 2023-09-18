package com.sirmarty.drinkcrafter.categories.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun CategoriesScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CategoryList()
    }
}


@Composable
fun CategoryList() {
    val categories = mutableListOf("Category 1", "Category 2", "Category 3")
    LazyColumn(Modifier.fillMaxWidth()) {
        items(categories) {
            CategoryItem(it)
        }
    }
}

@Composable
fun CategoryItem(category: String) {
    Card(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
            Text(text = category, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}
