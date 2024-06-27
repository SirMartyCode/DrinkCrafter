package com.sirmarty.drinkcrafter.ui.screens.search

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sirmarty.drinkcrafter.R
import com.sirmarty.drinkcrafter.ui.components.quickfinds.QuickFinds


@Composable
fun SearchScreen(
    onQuickFindClick: (String) -> Unit,
    onSearchByNameClick: () -> Unit,
    onRandomCocktailClick: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        SearchLayout(
            onQuickFindClick,
            onSearchByNameClick,
            onRandomCocktailClick
        )
    }
}

@Composable
fun SearchLayout(
    onQuickFindClick: (String) -> Unit,
    onSearchByNameClick: () -> Unit,
    onRandomCocktailClick: () -> Unit
) {
    val context = LocalContext.current

    Column(
        Modifier.padding(16.dp)
    ) {
        Text(
            text = context.getString(R.string.search_quick_finds),
            fontSize = 24.sp,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        QuickFinds(Modifier, onQuickFindClick)
        Spacer(Modifier.height(16.dp))
        CustomElevatedCard(
            onSearchByNameClick,
            R.drawable.image_search_cocktail,
            context.getString(R.string.search_search_by_name),
            ""
        )
        Spacer(Modifier.height(16.dp))
        CustomElevatedCard(
            onRandomCocktailClick,
            R.drawable.image_random_cocktail,
            context.getString(R.string.search_get_random_cocktail),
            ""
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomElevatedCard(
    onCardClick: () -> Unit,
    @DrawableRes resource: Int,
    cardText: String,
    contentDescription: String?,
) {
    ElevatedCard(
        onClick = { onCardClick() },
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Box(Modifier.fillMaxWidth()) {
            Image(
                painterResource(resource),
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(16f / 9f)
            )
            Text(
                text = cardText,
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
fun SearchPreview() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        SearchLayout(onQuickFindClick = {},
            onSearchByNameClick = {},
            onRandomCocktailClick = {})
    }
}