package com.sirmarty.drinkcrafter.ui.screens.ingredient

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sirmarty.drinkcrafter.R
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.entity.IngredientDetail
import com.sirmarty.drinkcrafter.ui.components.drinklist.DrinkItem
import com.sirmarty.drinkcrafter.ui.screens.UiState

@Composable
fun IngredientScreen(
    ingredient: String,
    onBackClick: () -> Unit,
    onDrinkClick: (Int) -> Unit,
    viewModel: IngredientViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.observeAsState(initial = UiState.Loading)
    val context = LocalContext.current

    // Avoid making the request each time the screen is recomposed
    LaunchedEffect(key1 = Unit) {
        viewModel.getData(ingredient)
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
                    IngredientView(
                        context,
                        (uiState as UiState.Success).value,
                        onDrinkClick,
                        onBackClick
                    )
                }
            }
        }
    }
}


//==================================================================================================
//region Private composable

// TODO: add top app bar and handle onBackClick
@Composable
private fun IngredientView(
    context: Context,
    data: Pair<IngredientDetail, List<Drink>>,
    onDrinkClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val ingredientDetail = data.first
    val drinkList = data.second

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),

    ) {
        item {
            IngredientInfo(context, ingredientDetail)
        }
        items(drinkList) { item ->
            DrinkItem(context, item, onDrinkClick)
        }
    }
}

@Composable
private fun IngredientInfo(
    context: Context,
    ingredientDetail: IngredientDetail
) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = ingredientDetail.name,
            Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline
        )
        Text(
            text = if (ingredientDetail.alcohol) {
                context.getString(R.string.ingredient_detail_alcoholic, ingredientDetail.abv)
            } else {
                context.getString(R.string.ingredient_detail_non_alcoholic)
            },
            Modifier.fillMaxWidth(),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        Spacer(Modifier.height(8.dp))
        ExpandableIngredientDescription(context, ingredientDetail.description)
    }

}

@Composable
private fun ExpandableIngredientDescription(
    context: Context,
    text: String,
    collapsedMaxLines: Int = 3
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(Modifier.clickable { isExpanded = !isExpanded }) {
        Text(
            text = text,
            fontSize = 14.sp,
            textAlign = TextAlign.Justify,
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLines,
            overflow = TextOverflow.Clip
        )
        if (!isExpanded) {
            Text(
                text = context.getString(R.string.ingredient_detail_read_more),
                fontSize = 14.sp,
                color = Color.Gray,
                textDecoration = TextDecoration.Underline
            )
        }
    }

}

//endregion
//==================================================================================================
//region Preview

@Preview
@Composable
fun IngredientScreenPreview() {

    val context = LocalContext.current
    val ingredientDetail = IngredientDetail(
        0,
        "Ingredient",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        "Type",
        true,
        "40"
    )
    val drinkList = listOf(
        Drink(0, "Drink 1", ""),
        Drink(1, "Drink 2", ""),
        Drink(2, "Drink 3", ""),
        Drink(3, "Drink 4", ""),
        Drink(4, "Drink 5", "")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        IngredientView(
            context,
            Pair(ingredientDetail, drinkList),
            onDrinkClick = {},
            onBackClick = {})
    }

}

//endregion