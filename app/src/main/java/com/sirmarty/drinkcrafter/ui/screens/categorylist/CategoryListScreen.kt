package com.sirmarty.drinkcrafter.ui.screens.categorylist

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.sirmarty.drinkcrafter.R
import com.sirmarty.drinkcrafter.ui.components.customloading.CustomLoading
import com.sirmarty.drinkcrafter.ui.components.errorlayout.ErrorLayout
import com.sirmarty.drinkcrafter.ui.components.screentitle.ScreenTitle
import com.sirmarty.drinkcrafter.ui.model.CategoryWithImage
import com.sirmarty.drinkcrafter.ui.screens.UiState
import com.sirmarty.drinkcrafter.ui.shared.clickableSingle


@Composable
fun CategoryListScreen(
    onCategoryClick: (String) -> Unit,
    viewModel: CategoryListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.observeAsState(initial = UiState.Loading)
    val showErrorDialog by viewModel.showErrorDialog.collectAsState()

    val context = LocalContext.current

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (uiState) {
            is UiState.Error -> {
                ErrorLayout(
                    throwable = (uiState as UiState.Error).throwable,
                    showErrorDialog = showErrorDialog,
                    onDismissRequest = { viewModel.hideErrorDialog() },
                    onConfirmation = { viewModel.retryRequest() }
                )
            }

            UiState.Loading -> {
                CustomLoading(Modifier.align(Alignment.Center))
            }

            is UiState.Success -> {
                CategoryListLayout(
                    context = context,
                    categories = (uiState as UiState.Success).value,
                    onCategoryClick = onCategoryClick
                )
            }
        }
    }
}

//==================================================================================================
//region Private composable

@Composable
private fun CategoryListLayout(
    context: Context,
    categories: List<CategoryWithImage>,
    onCategoryClick: (String) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        ScreenTitle(
            stringRes = R.string.categories_title,
            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
        )
        CategoryList(context, categories, onCategoryClick)
    }
}

@Composable
private fun CategoryList(
    context: Context,
    categories: List<CategoryWithImage>,
    onCategoryClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = categories,
            key = { it.name }
        ) {
            CategoryItem(context, it, onCategoryClick)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CategoryItem(
    context: Context,
    category: CategoryWithImage,
    onCategoryClick: (String) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle { onCategoryClick(category.name) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            GlideImage(
                model = category.image,
                contentDescription = context.getString(R.string.categories_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(16f / 9f)
            )
            Text(
                text = category.name,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )
        }
    }
}

//endregion
//==================================================================================================
//region Preview

@Preview
@Composable
private fun CategoryListPreview() {
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
            .background(MaterialTheme.colorScheme.background)
    ) {
        CategoryList(context, categories, onCategoryClick = {})
    }
}

//endregion