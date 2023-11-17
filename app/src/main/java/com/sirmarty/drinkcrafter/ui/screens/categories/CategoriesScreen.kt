package com.sirmarty.drinkcrafter.ui.screens.categories

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.sirmarty.drinkcrafter.ui.model.CategoryWithImage
import com.sirmarty.drinkcrafter.ui.model.QuickFind
import com.sirmarty.drinkcrafter.ui.screens.UiState


@Composable
fun CategoriesScreen(
    onCategoryClick: (String) -> Unit,
    onQuickFindClick: (String) -> Unit,
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
                Column {
                    QuickFinds(onQuickFindClick)
                    CategoryList(context, (uiState as UiState.Success).value, onCategoryClick)
                }
            }
        }
    }
}

@Composable
fun QuickFinds(onQuickFindClick: (String) -> Unit) {
    val quickFinds = QuickFind.values()
    LazyRow(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        items(quickFinds) {
            Column(Modifier.clickable { onQuickFindClick(it.ingredient) }) {
                Icon(painterResource(it.image), contentDescription = "")
                Text(text = it.ingredient)
            }
        }
    }
}


@Composable
fun CategoryList(context: Context, categories: List<CategoryWithImage>, onCategoryClick: (String) -> Unit) {
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
fun CategoryItem(context: Context, category: CategoryWithImage, onCategoryClick: (String) -> Unit) {
    ElevatedCard(
        onClick = { onCategoryClick(category.name) },
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Box(Modifier.fillMaxSize()) {
            Image(
                painterResource(category.image),
                contentDescription = context.getString(R.string.categories_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(16f / 9f)
            )
            Text(
                text = category.name,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun CategoryListPreview() {
    val context = LocalContext.current
    val categories = listOf(
        CategoryWithImage("Category 1", R.drawable.image_default_category),
        CategoryWithImage("Category 2", R.drawable.image_default_category),
        CategoryWithImage("Category 3", R.drawable.image_default_category),
        CategoryWithImage("Category 4", R.drawable.image_default_category),
        CategoryWithImage("Category 5", R.drawable.image_default_category)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CategoryList(context, categories, onCategoryClick = {})
    }
}