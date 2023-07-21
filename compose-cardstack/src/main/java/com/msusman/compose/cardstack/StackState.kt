package com.msusman.compose.cardstack

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.msusman.compose.cardstack.internal.Transformations
import com.msusman.compose.cardstack.utils.isNegative
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Created by Muhammad Usman : msusman97@gmail.com on 7/14/2023.
 */
@Composable
fun rememberStackState(): StackState {
    val scope = rememberCoroutineScope()
    val screenWidth: Float =
        with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val screenHeight: Float =
        with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp.dp.toPx() }
    return remember {
        StackState(
            scope = scope,
            screenWidth = screenWidth,
            screenHeight = screenHeight
        )
    }
}


class StackState(
    private val scope: CoroutineScope,
    private val screenWidth: Float,
    private val screenHeight: Float,
) {
    private var direction: Direction = Direction.None
    private var visibleCount: Int = 0
    private var stackElevationPx: Float = 0.0f
    private var scaleInterval: Float = 0.0f
    private var displacementThresholdpx: Float = 0.0f
    private var animationDuration: Duration = Duration.NORMAL
    private var rotationMaxDegree: Int = 0
    private var swipeDirection: SwipeDirection = SwipeDirection.FREEDOM
    private var swipeMethod: SwipeMethod = SwipeMethod.AUTOMATIC_AND_MANUAL
    var cardQueue = mutableListOf<CardSate>()
    private var dragOffsetX = 0f
    private var dragOffsetY = 0f
    private var onSwiped: (Int) -> Unit = { }
    private var onRewind: () -> Unit = { }
    private var topCardIndex = 0
    fun initilize(
        direction: Direction,
        visibleCount: Int,
        stackElevationPx: Float,
        scaleInterval: Float,
        displacementThresholdpx: Float,
        animationDuration: Duration,
        rotationMaxDegree: Int,
        swipeDirection: SwipeDirection,
        swipeMethod: SwipeMethod,
        onSwiped: (Int) -> Unit,
        onRewind: () -> Unit
    ) {
        this.direction = direction
        this.visibleCount = visibleCount
        this.stackElevationPx = stackElevationPx
        this.scaleInterval = scaleInterval
        this.displacementThresholdpx = displacementThresholdpx
        this.animationDuration = animationDuration
        this.rotationMaxDegree = rotationMaxDegree
        this.swipeDirection = swipeDirection
        this.swipeMethod = swipeMethod
        this.onSwiped = onSwiped
        this.onRewind = onRewind
    }


    private fun getSwipeDirection(): Direction {
        val isOffsetXNegative = dragOffsetX.isNegative()  //true if user dragged to left
        val isOffsetYNegative = dragOffsetY.isNegative()  //true if user dragged to upward
        val xThreshHoldReached = abs(dragOffsetX) > displacementThresholdpx
        val yThreshHoldReached = abs(dragOffsetY) > displacementThresholdpx
        return when {
            xThreshHoldReached && yThreshHoldReached -> {
                when {
                    isOffsetXNegative && isOffsetYNegative -> Direction.TopAndLeft
                    isOffsetXNegative -> Direction.BottomAndLeft
                    isOffsetYNegative -> Direction.TopAndRight
                    else -> Direction.BottomAndRight
                }
            }

            xThreshHoldReached -> if (isOffsetXNegative) Direction.Left else Direction.Right
            yThreshHoldReached -> if (isOffsetYNegative) Direction.Top else Direction.Bottom
            else -> Direction.None

        }
    }


    fun onDrag(change: PointerInputChange, dragAmount: Offset) = with(scope) {
        if(cardQueue.firstOrNull()?.isAnimating() == true)return@with
        if (swipeMethod == SwipeMethod.AUTOMATIC || swipeMethod == SwipeMethod.AUTOMATIC_AND_MANUAL) {
            when (swipeDirection) {
                SwipeDirection.FREEDOM -> {
                    dragOffsetX += dragAmount.x
                    dragOffsetY += dragAmount.y
                }

                SwipeDirection.HORIZONTAL -> dragOffsetX += dragAmount.x
                SwipeDirection.VERTICAL -> dragOffsetY += dragAmount.y
            }
            cardQueue.firstOrNull()?.snapToTranslation(Offset(dragOffsetX, dragOffsetY))
            val rotationZ = calculateRotation()
            cardQueue.firstOrNull()?.snapToRotation(rotationZ)
        }
        change.consume()
    }

    private fun calculateRotation(): Float {
        val resultantOffset = sqrt(dragOffsetX.pow(2) + dragOffsetY.pow(2))
        val calculatedRotationZ = (rotationMaxDegree * resultantOffset) / displacementThresholdpx
        val finalRotationZ = calculatedRotationZ.coerceAtMost(rotationMaxDegree.toFloat())
        return if (dragOffsetX.isNegative() || dragOffsetY.isNegative()) finalRotationZ.unaryMinus() else finalRotationZ
    }


    fun onDragEnd() {
        val swipeDirection: Direction = getSwipeDirection()
        if (swipeMethod.isAutomaticSwipeAllowed()) {
            swipeInternal(swipeDirection)
        }
    }

    fun <T> initCardQueue(items: List<T>) {
        cardQueue = List(items.size) { index ->
            val (tx, ty) = Transformations.calculateTranslation(
                index = index,
                direction = direction,
                visibleCount = visibleCount,
                stackElevationPx = stackElevationPx,
            )
            val (sx, sy) = Transformations.calculateScale(
                index = index,
                visibleCount = visibleCount,
                direction = direction,
                scaleInterval = scaleInterval,
            )
            CardSate(
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                scope = scope,
                offsetX = Animatable(tx),
                offsetY = Animatable(ty),
                scaleX = Animatable(sx),
                scaleY = Animatable(sy),
                rotation = Animatable(0.0f),
                zIndex = 1000.0f - index,
                animationDuration = animationDuration
            )
        }.toMutableList()
    }

    fun swipe(swipeDirection: Direction) {
        if (swipeMethod.isManualSwipeAllowed()) {
            swipeInternal(swipeDirection)
        }
    }

    private fun swipeInternal(swipeDirection: Direction) = scope.launch {
        if (cardQueue.firstOrNull()?.isAnimating() == true) return@launch
        cardQueue.firstOrNull()?.swipeToward(swipeDirection)
        if (swipeDirection != Direction.None) {
            cardQueue.removeFirstOrNull()
            cardQueue.forEachIndexed { index, cardSate ->
                val translateOffset = Transformations.calculateTranslation(
                    index = index,
                    direction = direction,
                    visibleCount = visibleCount,
                    stackElevationPx = stackElevationPx,
                )
                val scaleOffset = Transformations.calculateScale(
                    index = index,
                    visibleCount = visibleCount,
                    direction = direction,
                    scaleInterval = scaleInterval,
                )
                cardQueue[index].translateTo(translateOffset)
                cardQueue[index].scaleTo(scaleOffset)
            }
            onSwiped(topCardIndex++)
        }
        dragOffsetX = 0f
        dragOffsetY = 0f
    }

    fun rewind() {
        onRewind.invoke()
    }


}




