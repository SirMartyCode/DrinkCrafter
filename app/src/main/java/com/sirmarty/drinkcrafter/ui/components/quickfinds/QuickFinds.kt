package com.sirmarty.drinkcrafter.ui.components.quickfinds

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sirmarty.drinkcrafter.ui.model.QuickFind

@Composable
fun QuickFinds(modifier: Modifier = Modifier, onQuickFindClick: (String) -> Unit) {
    val context = LocalContext.current
    val quickFinds = QuickFind.values()
    LazyRow(
        modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(quickFinds) {
            QuickFindItem(context, it, onQuickFindClick)
        }
    }
}

@Composable
fun QuickFindItem(context: Context, quickFind: QuickFind, onQuickFindClick: (String) -> Unit) {
    ElevatedCard(Modifier.clickable { onQuickFindClick(quickFind.ingredient) }) {
        Column(
            Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(quickFind.image),
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
