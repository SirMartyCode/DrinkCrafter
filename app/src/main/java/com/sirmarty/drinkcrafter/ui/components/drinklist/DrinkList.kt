package com.sirmarty.drinkcrafter.ui.components.drinklist

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.sirmarty.drinkcrafter.R
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.ui.components.cardwithouttonalelevation.CardWithoutTonalElevation
import com.sirmarty.drinkcrafter.ui.shared.clickableSingle


@Composable
fun DrinkList(context: Context, drinks: List<Drink>, onDrinkClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(drinks) { item ->
            DrinkItem(context, item, onDrinkClick)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DrinkItem(context: Context, drink: Drink, onDrinkClick: (Int) -> Unit) {

    CardWithoutTonalElevation(
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle { onDrinkClick(drink.id) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .height(IntrinsicSize.Min)
        ) {
            GlideImage(
                model = drink.image,
                contentDescription = context.getString(R.string.drink_list_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(110.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = drink.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = context.getString(R.string.drink_list_show_details),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Preview
@Composable
fun DrinkListPreview() {
    val context = LocalContext.current
    val drinks = listOf(
        Drink(0, "Drink 1", ""),
        Drink(1, "Drink 2", ""),
        Drink(2, "Drink 3", ""),
        Drink(3, "Drink 4", ""),
        Drink(4, "Drink 5", "")
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DrinkList(context, drinks, onDrinkClick = {})
    }
}