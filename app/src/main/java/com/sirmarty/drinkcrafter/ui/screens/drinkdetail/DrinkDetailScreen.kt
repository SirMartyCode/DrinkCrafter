package com.sirmarty.drinkcrafter.ui.screens.drinkdetail

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsControllerCompat
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
    val view = LocalView.current
    val window = (context as? Activity)?.window
    val darkTheme = isSystemInDarkTheme()

    // Avoid making the request each time the screen is recomposed
    LaunchedEffect(key1 = Unit) {
        viewModel.getDrinkDetail(drinkId)
    }

    // Change status bar content colors to white
    DisposableEffect(Unit) {
        val windowInsetsController = window?.let { WindowInsetsControllerCompat(it, view) }
        windowInsetsController?.isAppearanceLightStatusBars = false

        // Put them back to their color when screen is not showing anymore
        onDispose {
            windowInsetsController?.isAppearanceLightStatusBars = !darkTheme
        }
    }

    // Avoiding the content to be drawn behind the navigation bar
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    Scaffold(
        // Allow the content to take up all available screen space
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, bottomPadding)

    ) { innerPadding ->
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
                        false,
                        onBackClick
                    )
                }
            }
        }
    }
}

@Composable
private fun DrinkDetailView(
    context: Context,
    drinkDetail: DrinkDetail,
    previewMode: Boolean,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            // Enable scrolling to the entire page
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            context,
            drinkDetail,
            onBackClick
        )
        Content(
            modifier = Modifier.fillMaxWidth(),
            context,
            drinkDetail,
            previewMode
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Image(
    modifier: Modifier,
    context: Context,
    drinkDetail: DrinkDetail,
    onBackClick: () -> Unit
) {
    Box(modifier) {
        GlideImage(
            model = drinkDetail.image,
            contentDescription = context.getString(R.string.drink_detail_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f / 1f)
                .align(Alignment.Center)
        )

        // We added this surface with a semitransparent black color as a darkening filter to make
        // more contrast between the light colors of the image and the status bar icons
        Surface(
            color = Color.Black.copy(alpha = 0.1f),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f / 1f)
        ) {}

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                // Keep the status bar padding, so the button doesn't overlap with it
                .safeDrawingPadding()
                .clip(CircleShape),
            colors = IconButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White,
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
    }
}

@Composable
private fun Content(
    modifier: Modifier,
    context: Context,
    drinkDetail: DrinkDetail,
    previewMode: Boolean
) {
    Box(modifier.fillMaxSize()) {
        Column(
            modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Header(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                drinkDetail
            )
            Spacer(modifier = Modifier.height(24.dp))
            Ingredients(context, drinkDetail)
            Spacer(modifier = Modifier.height(24.dp))
            Instructions(context, drinkDetail)
        }

        SaveButton(
            Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
            drinkId = drinkDetail.id,
            previewMode
        )
    }
}

@Composable
private fun Header(modifier: Modifier, drinkDetail: DrinkDetail) {
    Text(
        text = drinkDetail.name,
        modifier = modifier,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
    Text(
        text = "${drinkDetail.category} - ${drinkDetail.alcoholic}",
        modifier = modifier,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        color = Color.Gray
    )
}

@Composable
private fun Ingredients(context: Context, drinkDetail: DrinkDetail) {
    Text(
        text = context.getString(R.string.drink_detail_ingredients),
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(8.dp))

    // Since all ingredients will be displayed, we use a normal column instead of the lazy one
    Column(Modifier.fillMaxWidth()) {
        drinkDetail.ingredients.forEach { ingredient ->
            Text(
                text = "- ${ingredient.measure} ${ingredient.name}",
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun Instructions(context: Context, drinkDetail: DrinkDetail) {
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
private fun DrinkDetailPreview() {
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
        DrinkDetailView(context, drinkDetail, true) {}
    }
}