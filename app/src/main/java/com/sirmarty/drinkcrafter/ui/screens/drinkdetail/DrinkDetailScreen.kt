package com.sirmarty.drinkcrafter.ui.screens.drinkdetail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.sirmarty.drinkcrafter.R
import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.domain.entity.Ingredient
import com.sirmarty.drinkcrafter.ui.components.customloading.CustomLoading
import com.sirmarty.drinkcrafter.ui.components.customtopappbar.CustomTopAppBar
import com.sirmarty.drinkcrafter.ui.components.customtopappbar.CustomTopAppBarState
import com.sirmarty.drinkcrafter.ui.components.errorlayout.ErrorLayout
import com.sirmarty.drinkcrafter.ui.components.savebutton.SaveButton
import com.sirmarty.drinkcrafter.ui.screens.UiState
import com.sirmarty.drinkcrafter.ui.shared.clickableSingle

@Composable
fun DrinkDetailScreen(
    drinkId: Int,
    onIngredientClick: (String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: DrinkDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.observeAsState(initial = UiState.Loading)
    val topAppBarState by viewModel.topAppBarState.collectAsState()
    val showErrorDialog by viewModel.showErrorDialog.collectAsState()

    // Avoid making the request each time the screen is recomposed
    LaunchedEffect(key1 = Unit) {
        viewModel.getDrinkDetail(drinkId)
    }

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
            Box(Modifier.fillMaxSize()) {
                CustomLoading(Modifier.align(Alignment.Center))
            }
        }

        is UiState.Success -> {
            DrinkDetailLayout(
                drinkDetail = (uiState as UiState.Success).value,
                previewMode = false,
                topAppBarState = topAppBarState,
                onAppBarHeightUpdated = viewModel::updateAppBarHeight,
                onImageBottomOffsetUpdated = viewModel::updateImageBottomOffset,
                onTitleBottomOffsetUpdated = viewModel::updateTitleBottomOffset,
                onIngredientClick = onIngredientClick,
                onBackClick = onBackClick
            )
        }
    }
}

//==================================================================================================
//region Private composable

@Composable
private fun DrinkDetailLayout(
    drinkDetail: DrinkDetail,
    previewMode: Boolean,
    topAppBarState: CustomTopAppBarState,
    onAppBarHeightUpdated: (Float) -> Unit,
    onImageBottomOffsetUpdated: (Float) -> Unit,
    onTitleBottomOffsetUpdated: (Float) -> Unit,
    onIngredientClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CustomTopAppBar(
                Modifier.onGloballyPositioned { coordinates ->
                    onAppBarHeightUpdated(coordinates.positionInRoot().y + coordinates.size.height)
                },
                context,
                topAppBarState,
                drinkDetail.name,
                onBackClick,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                // Enable scrolling to the entire page
                .verticalScroll(rememberScrollState())
                .padding(
                    // Remove the top padding corresponding to the topBar
                    top = 0.dp,
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                context,
                drinkDetail,
                onImageBottomOffsetUpdated
            )
            Content(
                modifier = Modifier.fillMaxWidth(),
                context,
                drinkDetail,
                previewMode,
                onTitleBottomOffsetUpdated,
                onIngredientClick
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Image(
    modifier: Modifier,
    context: Context,
    drinkDetail: DrinkDetail,
    onImageBottomOffsetUpdated: (Float) -> Unit,
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
                .onGloballyPositioned { coordinates ->
                    onImageBottomOffsetUpdated(coordinates.positionInRoot().y + coordinates.size.height)
                }
        )

        // We added this surface with a semitransparent black color as a darkening filter to make
        // more contrast between the light colors of the image and the status bar icons
        Surface(
            color = Color.Black.copy(alpha = 0.1f),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f / 1f)
        ) {}
    }
}

@Composable
private fun Content(
    modifier: Modifier,
    context: Context,
    drinkDetail: DrinkDetail,
    previewMode: Boolean,
    onTitleBottomOffsetUpdated: (Float) -> Unit,
    onIngredientClick: (String) -> Unit
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
                drinkDetail,
                onTitleBottomOffsetUpdated
            )
            Spacer(modifier = Modifier.height(24.dp))
            Ingredients(context, drinkDetail, onIngredientClick)
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
private fun Header(
    modifier: Modifier,
    drinkDetail: DrinkDetail,
    onTitleBottomOffsetUpdated: (Float) -> Unit
) {
    Text(
        text = drinkDetail.name,
        modifier = modifier.onGloballyPositioned { coordinates ->
            onTitleBottomOffsetUpdated(coordinates.positionInRoot().y + coordinates.size.height)
        },
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onPrimary
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
private fun Ingredients(
    context: Context,
    drinkDetail: DrinkDetail,
    onIngredientClick: (String) -> Unit
) {
    Text(
        text = context.getString(R.string.drink_detail_ingredients),
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(8.dp))

    // Since all ingredients will be displayed, we use a normal column instead of the lazy one
    Column(Modifier.fillMaxWidth()) {
        drinkDetail.ingredients.forEach { ingredient ->
            Text(
                modifier = Modifier.clickableSingle { onIngredientClick(ingredient.name) },
                text = "- $ingredient",
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun Instructions(context: Context, drinkDetail: DrinkDetail) {
    Text(
        text = context.getString(R.string.drink_detail_instructions),
        fontSize = 18.sp,
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

//endregion
//==================================================================================================
//region Preview

@Preview
@Composable
private fun DrinkDetailPreview() {
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

    DrinkDetailLayout(
        drinkDetail,
        true,
        CustomTopAppBarState.TRANSPARENT,
        onAppBarHeightUpdated = {},
        onImageBottomOffsetUpdated = {},
        onTitleBottomOffsetUpdated = {},
        onIngredientClick = {},
        onBackClick = {}
    )
}

//endregion