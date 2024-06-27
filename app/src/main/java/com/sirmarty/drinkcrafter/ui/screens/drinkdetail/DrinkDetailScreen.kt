package com.sirmarty.drinkcrafter.ui.screens.drinkdetail

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
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

private enum class TopBarState {
    TRANSPARENT,
    WHITE_NO_TITLE,
    WHITE_TITLE
}

@Composable
fun DrinkDetailScreen(
    drinkId: Int,
    onBackClick: () -> Unit,
    viewModel: DrinkDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.observeAsState(initial = UiState.Loading)

    // Avoid making the request each time the screen is recomposed
    LaunchedEffect(key1 = Unit) {
        viewModel.getDrinkDetail(drinkId)
    }

    when (uiState) {
        is UiState.Error -> {
            Box(Modifier.fillMaxSize()) {
                Text(
                    text = (uiState as UiState.Error).throwable.message
                        ?: "UNKNOWN ERROR",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        UiState.Loading -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }

        is UiState.Success -> {
            DrinkDetailLayout(
                (uiState as UiState.Success).value,
                false,
                onBackClick
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
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    var appBarHeight by remember { mutableStateOf(0f) }
    val imageBottomOffset = remember { mutableStateOf<Float?>(null) }
    val titleBottomOffset = remember { mutableStateOf<Float?>(null) }

    val topBarState = calculateTopAppBarState(appBarHeight, imageBottomOffset, titleBottomOffset)

    Scaffold(
        topBar = {
            TransparentTopAppBar(
                Modifier
                    .onGloballyPositioned { coordinates ->
                        appBarHeight = coordinates.positionInRoot().y + coordinates.size.height
                    },
                context,
                drinkDetail,
                onBackClick,
                topBarState
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                imageBottomOffset
            )
            Content(
                modifier = Modifier.fillMaxWidth(),
                context,
                drinkDetail,
                previewMode,
                titleBottomOffset
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransparentTopAppBar(
    modifier: Modifier,
    context: Context,
    drinkDetail: DrinkDetail,
    onBackClick: () -> Unit,
    topBarState: TopBarState
) {
    val view = LocalView.current
    val window = (context as? Activity)?.window
    val darkTheme = isSystemInDarkTheme()
    var isAppearanceLightStatusBars by remember { mutableStateOf(false) }

    val backgroundColor: Color
    val contentColor: Color
    val title: String

    when (topBarState) {
        TopBarState.TRANSPARENT -> {
            backgroundColor = Color.Transparent
            contentColor = Color.White
            title = ""
            isAppearanceLightStatusBars = false
        }
        TopBarState.WHITE_NO_TITLE -> {
            backgroundColor = Color.White
            contentColor = Color.Black
            title = ""
            isAppearanceLightStatusBars = !darkTheme
        }
        TopBarState.WHITE_TITLE -> {
            backgroundColor = Color.White
            contentColor = Color.Black
            title = drinkDetail.name
            isAppearanceLightStatusBars = !darkTheme
        }
    }

    // Change status bar content color to match top bar content color
    val windowInsetsController = window?.let { WindowInsetsControllerCompat(it, view) }
    windowInsetsController?.isAppearanceLightStatusBars = isAppearanceLightStatusBars

    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                Modifier.padding(end = 16.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = contentColor
        ),
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.clip(CircleShape),
                colors = IconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = contentColor,
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
    )

    // Put the content color back to their default value when screen is not showing anymore
    DisposableEffect(Unit) {
        onDispose {
            windowInsetsController?.isAppearanceLightStatusBars = !darkTheme
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Image(
    modifier: Modifier,
    context: Context,
    drinkDetail: DrinkDetail,
    imageBottomOffset: MutableState<Float?>
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
                    imageBottomOffset.value =
                        coordinates.positionInRoot().y + coordinates.size.height
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
    titleBottomOffset: MutableState<Float?>
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
                titleBottomOffset
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
private fun Header(
    modifier: Modifier,
    drinkDetail: DrinkDetail,
    titleBottomOffset: MutableState<Float?>
) {
    Text(
        text = drinkDetail.name,
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                titleBottomOffset.value =
                    coordinates.positionInRoot().y + coordinates.size.height
            },
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

//endregion
//==================================================================================================
//region Private methods

private fun calculateTopAppBarState(
    appBarHeight: Float,
    imageBottomOffset: MutableState<Float?>,
    titleBottomOffset: MutableState<Float?>
): TopBarState {
    return when {
        imageBottomOffset.value != null && titleBottomOffset.value != null -> {
            if (imageBottomOffset.value!! <= appBarHeight) {
                if (titleBottomOffset.value!! <= appBarHeight) {
                    TopBarState.WHITE_TITLE
                } else {
                    TopBarState.WHITE_NO_TITLE
                }
            } else {
                TopBarState.TRANSPARENT
            }
        }
        else -> {
            TopBarState.TRANSPARENT
        }
    }
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

    DrinkDetailLayout(drinkDetail, true) {}
}

//endregion