package com.sirmarty.drinkcrafter.core.categories.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirmarty.drinkcrafter.core.categories.domain.entity.Category

@Composable
fun CategoriesScreen(
    onCategoryClick: (String) -> Unit,
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    viewModel.getCategories()

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CategoryList(viewModel, onCategoryClick)
    }
}


@Composable
fun CategoryList(viewModel: CategoriesViewModel, onCategoryClick: (String) -> Unit) {
    val categories by viewModel.categories.observeAsState(initial = emptyList())

    LazyColumn(Modifier.fillMaxWidth()) {
        items(categories) {
            CategoryItem(it, onCategoryClick)
        }
    }
}

@Composable
fun CategoryItem(category: Category, onCategoryClick: (String) -> Unit) {
    Card(
        Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onCategoryClick(category.name) }) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = category.name, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}
