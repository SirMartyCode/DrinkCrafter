package com.sirmarty.drinkcrafter.ui.screens.drinkdetail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.domain.entity.Ingredient
import com.sirmarty.drinkcrafter.ui.components.savebutton.SaveButton
import com.sirmarty.drinkcrafter.ui.screens.UiState

@Composable
fun DrinkDetailScreen(
    drinkId: Int,
    onBackClick: () -> Unit,
    viewModel: DrinkDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.observeAsState(initial = UiState.Loading)
    val context = LocalContext.current

    // Avoid making the request each time the screen is recomposed
    LaunchedEffect(key1 = Unit) {
        viewModel.getDrinkDetail(drinkId)
    }

    Scaffold { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                    DrinkDetailView(
                        context,
                        (uiState as UiState.Success).value,
                        onBackClick
                    )
                }
            }
        }
    }
}

@Composable
fun DrinkDetailView(
    context: Context,
    drinkDetail: DrinkDetail,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            //.imePadding()
            .fillMaxWidth()
    ) {
        Header(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            context,
            drinkDetail,
            onBackClick
        )
        Content(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            context,
            drinkDetail
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Header(modifier: Modifier, context: Context, drinkDetail: DrinkDetail, onBackClick: () -> Unit) {
    Box(modifier) {
        GlideImage(
            model = drinkDetail.image,
            contentDescription = context.getString(R.string.drink_detail_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(MaterialTheme.shapes.medium)
                .align(Alignment.Center)
        )
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(8.dp)
                // Keep the status bar padding, so the button doesn't overlap with it
                //.statusBarsPadding().navigationBarsPadding()
                .clip(CircleShape),
            colors = IconButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black,
                // It should never be disabled
                disabledContainerColor = Color.Unspecified,
                disabledContentColor = Color.Unspecified
            )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = context.getString(R.string.drink_detail_back_arrow)
            )
        }
        SaveButton(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp),
            drinkId = drinkDetail.id
        )
    }
}

@Composable
fun Content(modifier: Modifier, context: Context, drinkDetail: DrinkDetail) {
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicInfo(drinkDetail)
        Spacer(modifier = Modifier.height(24.dp))
        Ingredients(context, drinkDetail)
        Spacer(modifier = Modifier.height(24.dp))
        Instructions(context, drinkDetail)
    }
}

@Composable
fun BasicInfo(drinkDetail: DrinkDetail) {
    Text(text = drinkDetail.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    Text(
        text = "${drinkDetail.category} - ${drinkDetail.alcoholic}",
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Gray
    )
}

@Composable
fun Ingredients(context: Context, drinkDetail: DrinkDetail) {
    Text(
        text = context.getString(R.string.drink_detail_ingredients),
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyColumn(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        items(drinkDetail.ingredients) { ingredient ->
            Text(
                text = "- ${ingredient.measure} ${ingredient.name}",
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun Instructions(context: Context, drinkDetail: DrinkDetail) {
    Text(
        text = context.getString(R.string.drink_detail_instructions),
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold
    )
    Text(
        text = "(${drinkDetail.glass})",
        fontSize = 16.sp,
        color = Color.Gray
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = drinkDetail.instructions,
        fontSize = 14.sp, textAlign = TextAlign.Justify
    )
}


@Preview
@Composable
fun DrinkDetailPreview() {
    val context = LocalContext.current
    val ingredientList = listOf(
        Ingredient("Ingredient 1", "measure 1"),
        Ingredient("Ingredient 2", "measure 2"),
        Ingredient("Ingredient 3", "measure 3"),
        Ingredient("Ingredient 4", "measure 4"),
        Ingredient("Ingredient 5", "measure 5")
    )
    val drinkDetail = DrinkDetail(
        0,
        "Drink detail",
        "Category 1",
        "Alcoholic",
        "Glass type",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        "image",
        ingredientList
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        DrinkDetailView(context, drinkDetail) {}
    }
}


/*
@Composable
private fun SetTransparentStatusBar() {
    val darkTheme = isSystemInDarkTheme()
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
}

 */