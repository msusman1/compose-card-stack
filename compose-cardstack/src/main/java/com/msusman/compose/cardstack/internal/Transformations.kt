package com.msusman.compose.cardstack.internal

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.layout
import com.msusman.compose.cardstack.Direction
import kotlin.math.pow

/**
 * Created by Muhammad Usman : msusman97@gmail.com on 7/19/2023.
 */
internal object Transformations {

    fun calculateTranslation(
        index: Int,
        direction: Direction,
        visibleCount: Int,
        stackElevationPx: Float
    ): Offset {
        var translationX = 0f
        var translationY = 0f
        val translationIndex = if (index <= visibleCount) index else visibleCount
        when (direction) {
            Direction.Top -> {
                translationY = -stackElevationPx * translationIndex
            }

            Direction.Bottom -> {
                translationY = stackElevationPx * translationIndex
            }

            Direction.Left -> {
                translationX = -stackElevationPx * translationIndex
                translationY = 0f
            }

            Direction.Right -> {
                translationX = stackElevationPx * translationIndex
            }

            Direction.TopAndLeft -> {
                translationX = -stackElevationPx * translationIndex
                translationY = -stackElevationPx * translationIndex
            }

            Direction.TopAndRight -> {
                translationX = stackElevationPx * translationIndex
                translationY = -stackElevationPx * translationIndex
            }

            Direction.BottomAndLeft -> {
                translationX = -stackElevationPx * translationIndex
                translationY = stackElevationPx * translationIndex
            }

            Direction.BottomAndRight -> {
                translationX = stackElevationPx * translationIndex
                translationY = stackElevationPx * translationIndex
            }

            else -> {
                translationX = 0f
                translationY = 0f
            }

        }
        return Offset(translationX, translationY)

    }

    fun calculateScale(
        index: Int,
        visibleCount: Int,
        direction: Direction,
        scaleInterval: Float
    ): Offset {
        val scaleIndex = if (index <= visibleCount) index else visibleCount
        return when (direction) {
            Direction.Top, Direction.Bottom -> Offset(scaleInterval.pow(scaleIndex), 1f)
            Direction.Left, Direction.Right -> Offset(1f, scaleInterval.pow(scaleIndex))
            else -> Offset(1f, 1f)
        }
    }
}

fun Modifier.visible(
    visible: Boolean = true
) = this.then(Modifier.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    if (visible) {
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    } else {
        layout(0, 0) {}
    }
})

data class Item(
    val url: String? = null,
    val text: String = "",
    val subText: String = ""
)
