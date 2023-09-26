package com.sirmarty.drinkcrafter.drink.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sirmarty.drinkcrafter.R
import com.sirmarty.drinkcrafter.drink.domain.entity.DrinkDetail

@Composable
fun DrinkDetailScreen(viewModel: DrinkDetailViewModel, drinkId: Int) {
    viewModel.getDrinkDetail(drinkId)

    val drink by viewModel.drinkDetail.observeAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(Color.White)
    ) {
        Header(drink)
        Spacer(Modifier.height(16.dp))
        Body(drink)
    }
}

@Composable
fun Header(drinkDetail: DrinkDetail?) {
    if (drinkDetail == null)
        return

    Column(Modifier.fillMaxWidth()) {
        Text(
            text = drinkDetail.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Spacer(Modifier.height(4.dp))
        Row {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Drink image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(50.dp)
            )
            Spacer(Modifier.width(8.dp))
            LazyColumn {
                items(drinkDetail.ingredients) {
                    Row(Modifier.fillMaxWidth()) {
                        Text(text = "- ${it.name}")
                        it.measure?.let { measure ->
                            Text(
                                text = measure,

                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Body(drinkDetail: DrinkDetail?) {
    if (drinkDetail == null)
        return

    Column {
        Spacer(Modifier.height(8.dp))
        Text(text = drinkDetail.instructions)
    }
}