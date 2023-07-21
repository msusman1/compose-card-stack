package com.msusman.compose.cardstack.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

/**
 * Created by Muhammad Usman : msusman97@gmail.com on 7/18/2023.
 */
@Composable
fun MyImageCard(modifier: Modifier = Modifier, item: Item) {
    Box(
        modifier
    ) {
        if (item.url != null) {
            AsyncImage(
                model = item.url,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(color = Color.Black.copy(alpha = 0.4f))
                .padding(10.dp)
        ) {
            Text(
                text = item.text,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            Text(
                text = item.subText,
                color = Color.White,
                fontSize = 16.sp,
            )
        }
    }
}