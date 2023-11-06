package com.sirmarty.drinkcrafter.ui.screens.categories

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirmarty.drinkcrafter.R
import com.sirmarty.drinkcrafter.domain.entity.Category
import com.sirmarty.drinkcrafter.ui.screens.UiState


@Composable
fun CategoriesScreen(
    onCategoryClick: (String) -> Unit,
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.observeAsState(initial = UiState.Loading)
    val context = LocalContext.current

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when (uiState) {
            is UiState.Error -> {
                Text(
                    text = (uiState as UiState.Error).throwable.message
                        ?: "UNKNOWN ERROR",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            UiState.Loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            is UiState.Success -> {
                CategoryList(context, (uiState as UiState.Success).value, onCategoryClick)
            }
        }
    }
}


@Composable
fun CategoryList(context: Context, categories: List<Category>, onCategoryClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) {
            CategoryItem(context, it, onCategoryClick)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItem(context: Context, category: Category, onCategoryClick: (String) -> Unit) {
    ElevatedCard(
        onClick = { onCategoryClick(category.name) },
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Box(Modifier.fillMaxSize()) {
            Image(
                painterResource(R.drawable.image_cocktail),
                contentDescription = context.getString(R.string.categories_image),
                contentScale = ContentScale.Crop,
                alpha = 0.8f,
                modifier = Modifier.aspectRatio(16f / 9f)
            )
            Text(
                text = category.name,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center).padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun CategoryListPreview() {
    val context = LocalContext.current
    val categories = listOf(
        Category("Category 1"),
        Category("Category 2"),
        Category("Category 3"),
        Category("Category 4"),
        Category("Category 5")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CategoryList(context, categories, onCategoryClick = {})
    }
}