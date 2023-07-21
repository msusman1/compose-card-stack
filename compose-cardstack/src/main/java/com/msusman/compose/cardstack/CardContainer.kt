package com.msusman.compose.cardstack

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.msusman.compose.cardstack.internal.visible

/**
 * Created by Muhammad Usman : msusman97@gmail.com on 7/19/2023.
 */
@Composable
fun CardContainer(stackState: StackState, index: Int, content: @Composable () -> Unit) {
    val cardState: CardSate = stackState.cardQueue[index]
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(cardState.zIndex)
            .visible(cardState.shouldVisible)
            .graphicsLayer {
                translationX = cardState.offsetX.value
                translationY = cardState.offsetY.value
                scaleX = cardState.scaleX.value
                scaleY = cardState.scaleY.value
                rotationZ = cardState.rotation.value
            }
            .shadow(4.dp, RoundedCornerShape(12.dp)),
    ) {
        content()
    }
}