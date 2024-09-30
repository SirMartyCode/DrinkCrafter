package com.sirmarty.drinkcrafter.ui.components.quickfinds

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.sirmarty.drinkcrafter.ui.components.cardwithouttonalelevation.CardWithoutTonalElevation
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun QuickFindItem(quickFind: QuickFind, onQuickFindClick: (String) -> Unit) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clip(CardDefaults.shape)
            .clickable { onQuickFindClick(quickFind.ingredient) }

    ) {
        CardWithoutTonalElevation {
            GlideImage(
                model = quickFind.image,
                contentDescription = "",
                modifier = Modifier.height(110.dp).width(100.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = context.getString(quickFind.text),
            fontSize = 16.sp,
        )
    }
}

@Preview
@Composable
fun QuickFindsPreview() {
    Box(Modifier.background(MaterialTheme.colorScheme.background)) {
        QuickFinds {}
    }
}
