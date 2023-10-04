package com.sirmarty.drinkcrafter.ui.screens.drinklist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.sirmarty.drinkcrafter.domain.entity.Drink

@Composable
fun DrinkListScreen(
    categoryName: String,
    onDrinkClick: (Int) -> Unit,
    viewModel: DrinkListViewModel = hiltViewModel(),
) {
    viewModel.getDrinkList(categoryName)

    val drinks by viewModel.drinks.observeAsState(initial = emptyList())

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        DrinkList(drinks, onDrinkClick)
    }
}

@Composable
fun DrinkList(drinks: List<Drink>, onDrinkClick: (Int) -> Unit) {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
        itemsIndexed(drinks) { index, item ->
            DrinkItem(item, onDrinkClick)
            if (index < drinks.lastIndex) {
                HorizontalDivider(color = Color.Gray, thickness = 0.5.dp)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DrinkItem(drink: Drink, onDrinkClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onDrinkClick(drink.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = drink.image,
            contentDescription = "Drink image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(8.dp))
        Text(text = drink.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}