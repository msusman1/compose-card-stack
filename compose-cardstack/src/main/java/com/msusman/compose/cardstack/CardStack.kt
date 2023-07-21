package com.msusman.compose.cardstack

import androidx.annotation.IntRange
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.pow


/**
 * A composable that create cards stack
 * @param modifier Modifier for the this composable adding padding if required in modifier
 * @param stackState use this state to programmatically swipe and rewind
 * @param stackDirection card stack direction
 * @param visibleCount no of cards to be visible below the top card (@IntRange(from = 0, to = maxSize )
 * @param cardElevation determine how much portion of underline cards will be displayed
 * @param scaleRatio determine how much underline cards be scale down value fo 0.95f means 90% of original size
 * @param displacementThreshold thresh hold value to make the card swipe
 * @param animationDuration duration of the card animation
 * @param rotationMaxDegree maximum degree at which card can rotate
 * @param swipeDirection direction where card can move
 * @param swipeMethod set swipe methods either manual(through code) or automatic(through swipe)
 * @param onSwiped callback to card state change (@Position)
 * @author msusman97@gmail.com
 */
@Composable
fun <T> CardStack(
    modifier: Modifier = Modifier,
    stackState: StackState = rememberStackState(),
    stackDirection: Direction = Direction.Bottom,
    @IntRange(from = 1) visibleCount: Int = 3,
    cardElevation: Dp = 10.dp,
    scaleRatio: Float = 0.95f,
    displacementThreshold: Dp = 60.dp,
    animationDuration: Duration = Duration.NORMAL,
    @IntRange(from = 0, to = 360) rotationMaxDegree: Int = 20,
    swipeDirection: SwipeDirection = SwipeDirection.FREEDOM,
    swipeMethod: SwipeMethod = SwipeMethod.AUTOMATIC_AND_MANUAL,
    onSwiped: (Int) -> Unit = { _ -> },
    items: List<T>,
    content: @Composable (item: T) -> Unit
) {
    require(visibleCount in 1 until items.size) {
        "visibilityCount must be greater than 0 and less than items size"
    }
    require(cardElevation >= 0.dp) {
        "cardElevation must not be negative"
    }
    require(scaleRatio in 0.0f..1.0f) {
        "scaleRatio must be between 0.0 and 1.0 (inclusive)"
    }
    require(rotationMaxDegree in 0..360) {
        "rotationMaxDegree must be between 0 and 360 (inclusive)"
    }
    val density = LocalDensity.current
    val onCardSwiped: (Int) -> Unit = {
        onSwiped.invoke(it)
    }
    var rewind by remember { mutableStateOf(0) }
    val onRewind: () -> Unit = {
        rewind += 1
    }
    //Initialize stack state
    stackState.initilize(
        direction = stackDirection,
        visibleCount = visibleCount,
        stackElevationPx = with(density) { cardElevation.toPx() },
        scaleInterval = scaleRatio,
        displacementThresholdpx = with(density) { displacementThreshold.toPx() },
        animationDuration = animationDuration,
        rotationMaxDegree = rotationMaxDegree,
        swipeDirection = swipeDirection,
        swipeMethod = swipeMethod,
        onSwiped = onCardSwiped,
        onRewind = onRewind,
    )

    //calculate stack padding based on number of card visible
    val scalePadding =
        (1..visibleCount).sumOf { cardElevation.times((1 - scaleRatio).pow(it)).value.toDouble() }.dp
    val stackPadding = cardElevation.times(visibleCount)
    val (stackPaddingHorizontal, stackPaddingVertical) = when (stackDirection) {
        Direction.None -> 0.dp to 0.dp
        Direction.Top -> 0.dp to stackPadding.minus(scalePadding)
        Direction.Bottom -> 0.dp to stackPadding.minus(scalePadding)
        Direction.Left -> stackPadding.minus(scalePadding) to 0.dp
        Direction.Right -> stackPadding.minus(scalePadding) to 0.dp
        Direction.TopAndLeft -> stackPadding to stackPadding
        Direction.TopAndRight -> stackPadding to stackPadding
        Direction.BottomAndLeft -> stackPadding to stackPadding
        Direction.BottomAndRight -> stackPadding to stackPadding
    }
    remember(key1 = rewind) {
        stackState.initCardQueue(items);""
    }
    Box(
        modifier = modifier
            .padding(
                horizontal = stackPaddingHorizontal,
                vertical = stackPaddingVertical
            )
            .pointerInput(key1 = Unit) {
                detectDragGestures(
                    onDrag = stackState::onDrag,
                    onDragEnd = stackState::onDragEnd
                )
            }
    ) {
        items.forEachIndexed { index, item ->
            CardContainer(stackState = stackState, index = index) {
                content(item)
            }
        }
    }
}





