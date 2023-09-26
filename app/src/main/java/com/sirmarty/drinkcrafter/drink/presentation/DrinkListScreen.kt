package com.sirmarty.drinkcrafter.drink.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sirmarty.drinkcrafter.R
import com.sirmarty.drinkcrafter.drink.domain.entity.Drink

@Composable
fun DrinkListScreen(
    viewModel: DrinkListViewModel,
    categoryName: String,
    onNavigateToDrinkDetail: (Int) -> Unit
) {
    viewModel.getDrinkList(categoryName)

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        DrinkList(viewModel, onNavigateToDrinkDetail)
    }
}

@Composable
fun DrinkList(viewModel: DrinkListViewModel, onNavigateToDrinkDetail: (Int) -> Unit) {
    val drinks by viewModel.drinks.observeAsState(initial = emptyList())

    LazyColumn(Modifier.fillMaxWidth()) {
        items(drinks) {
            DrinkItem(it, onNavigateToDrinkDetail)
        }
    }
}

@Composable
fun DrinkItem(drink: Drink, onNavigateToDrinkDetail: (Int) -> Unit) {
    Card(
        Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onNavigateToDrinkDetail(drink.id) }) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Drink image"
            )
            Spacer(Modifier.width(8.dp))
            Text(text = drink.name, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}