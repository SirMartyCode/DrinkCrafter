package com.sirmarty.drinkcrafter.ui.screens.drinkdetail

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail

@Composable
fun DrinkDetailScreen(
    drinkId: Int,
    viewModel: DrinkDetailViewModel = hiltViewModel()
) {
    viewModel.getDrinkDetail(drinkId)

    val drink by viewModel.drinkDetail.observeAsState(initial = null)

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

@OptIn(ExperimentalGlideComposeApi::class)
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
            GlideImage(
                model = drinkDetail.image,
                contentDescription = "Drink image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
            )
            Spacer(Modifier.width(8.dp))
            LazyColumn {
                items(drinkDetail.ingredients) {
                    Text(text = "- ${it.measure} ${it.name}")
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