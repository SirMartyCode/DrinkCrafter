package com.sirmarty.drinkcrafter.ui.components.quickfinds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.sirmarty.drinkcrafter.ui.model.QuickFind

@Composable
fun QuickFinds(modifier: Modifier = Modifier, onQuickFindClick: (String) -> Unit) {
    val quickFinds = QuickFind.values()
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = quickFinds,
            key = { it.ingredient }
        ) {
            QuickFindItem(it, onQuickFindClick)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun QuickFindItem(quickFind: QuickFind, onQuickFindClick: (String) -> Unit) {
    val context = LocalContext.current
    ElevatedCard(
        modifier = Modifier.padding(8.dp),
        onClick = { onQuickFindClick(quickFind.ingredient) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )) {
        Column(
            Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                model = quickFind.image,
                contentDescription = "",
                modifier = Modifier.size(64.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(text = context.getString(quickFind.text))
        }
    }
}

@Preview
@Composable
fun QuickFindsPreview() {
    Box(Modifier.background(Color.White)) {
        QuickFinds {}
    }
}
