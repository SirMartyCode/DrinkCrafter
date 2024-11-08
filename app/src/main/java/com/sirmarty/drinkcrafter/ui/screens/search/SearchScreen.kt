package com.sirmarty.drinkcrafter.ui.screens.search

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.sirmarty.drinkcrafter.R
import com.sirmarty.drinkcrafter.ui.components.quickfinds.QuickFinds
import com.sirmarty.drinkcrafter.ui.components.screentitle.ScreenTitle
import com.sirmarty.drinkcrafter.ui.shared.clickableSingle


@Composable
fun SearchScreen(
    onQuickFindClick: (String) -> Unit,
    onSearchByNameClick: () -> Unit,
    onSearchByIngredientClick: () -> Unit,
    onRandomCocktailClick: () -> Unit
) {
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        ScreenTitle(R.string.search_quick_finds)
        Spacer(Modifier.height(8.dp))
        QuickFinds(Modifier.fillMaxWidth(), onQuickFindClick)
        ScreenTitle(R.string.search_searches)
        Spacer(Modifier.height(16.dp))
        CustomElevatedCard(
            onSearchByNameClick,
            R.drawable.image_search_cocktail,
            context.getString(R.string.search_search_by_name),
            context.getString(R.string.search_search_by_name)
        )
        Spacer(Modifier.height(16.dp))
        CustomElevatedCard(
            onSearchByIngredientClick,
            R.drawable.image_search_ingredient,
            context.getString(R.string.search_search_by_ingredient),
            context.getString(R.string.search_search_by_ingredient)
        )
        Spacer(Modifier.height(16.dp))
        CustomElevatedCard(
            onRandomCocktailClick,
            R.drawable.image_random_cocktail,
            context.getString(R.string.search_get_random_cocktail),
            context.getString(R.string.search_get_random_cocktail)
        )
    }

}

//==================================================================================================
//region Private composable

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CustomElevatedCard(
    onCardClick: () -> Unit,
    @DrawableRes resource: Int,
    cardText: String,
    contentDescription: String?,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle { onCardClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Box(Modifier.fillMaxWidth()) {
            GlideImage(
                model = resource,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(16f / 9f)
            )
            Text(
                text = cardText,
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
private fun SearchPreview() {
    SearchScreen(
        onQuickFindClick = {},
        onSearchByNameClick = {},
        onSearchByIngredientClick = {},
        onRandomCocktailClick = {}
    )
}

//endregion