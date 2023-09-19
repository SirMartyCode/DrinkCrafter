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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sirmarty.drinkcrafter.categories.domain.entity.Category

@Composable
fun CategoriesScreen(viewModel: CategoriesViewModel) {
    viewModel.getCategories()

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CategoryList(viewModel)
    }
}


@Composable
fun CategoryList(viewModel: CategoriesViewModel) {
    val categories by viewModel.categories.observeAsState(initial = emptyList())

    LazyColumn(Modifier.fillMaxWidth()) {
        items(categories) {
            CategoryItem(it)
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
    Card(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
            Text(text = category.name, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}
