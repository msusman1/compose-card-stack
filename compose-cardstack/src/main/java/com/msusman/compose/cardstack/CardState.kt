package com.msusman.compose.cardstack

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.tween
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by Muhammad Usman : msusman97@gmail.com on 7/19/2023.
 */

class CardSate(
    private val screenWidth: Float,
    private val screenHeight: Float,
    private val scope: CoroutineScope,
    val offsetX: Animatable<Float, AnimationVector1D>,
    val offsetY: Animatable<Float, AnimationVector1D>,
    val scaleX: Animatable<Float, AnimationVector1D>,
    val scaleY: Animatable<Float, AnimationVector1D>,
    val rotation: Animatable<Float, AnimationVector1D>,
    val zIndex: Float,
    animationDuration: Duration,
) {
    private val center = Offset(0f, 0f)
    private val centerAnimationSpec: AnimationSpec<Float> = SpringSpec()
    private val animationSpec: AnimationSpec<Float> =
        tween(durationMillis = animationDuration.duration)

    private fun returnCenter() = with(scope) {
        launch { offsetX.animateTo(center.x, centerAnimationSpec) }
        launch { offsetY.animateTo(center.y, centerAnimationSpec) }
        launch { rotation.animateTo(0.0f, centerAnimationSpec) }
    }

    val shouldVisible get() = true
    suspend fun swipeToward(swipeDirection: Direction) = with(scope) {

        when (swipeDirection) {
            Direction.Left -> {
                launch { offsetX.animateTo(-screenWidth * 1.5f, animationSpec) }
            }

            Direction.Right -> {
                launch { offsetX.animateTo(screenWidth * 1.5f, animationSpec) }
            }

            Direction.Top -> {
                launch { offsetY.animateTo(-screenHeight * 1.5f, animationSpec) }
            }

            Direction.Bottom -> {
                launch { offsetY.animateTo(screenHeight * 1.5f, animationSpec) }
            }

            Direction.TopAndLeft -> {
                launch { offsetX.animateTo(-screenWidth * 1.5f, animationSpec) }
                launch { offsetY.animateTo(-screenHeight * 1.5f, animationSpec) }
            }

            Direction.TopAndRight -> {
                launch { offsetX.animateTo(screenWidth * 1.5f, animationSpec) }
                launch { offsetY.animateTo(-screenHeight * 1.5f, animationSpec) }
            }

            Direction.BottomAndLeft -> {
                launch { offsetX.animateTo(-screenWidth * 1.5f, animationSpec) }
                launch { offsetY.animateTo(screenHeight * 1.5f, animationSpec) }
            }

            Direction.BottomAndRight -> {
                launch { offsetX.animateTo(screenWidth * 1.5f, animationSpec) }
                launch { offsetY.animateTo(screenHeight * 1.5f, animationSpec) }
            }

            Direction.None -> {
                returnCenter()
            }

        }
    }

    fun translateTo(translateOffset: Offset) = with(scope) {
        launch { offsetX.animateTo(translateOffset.x, animationSpec) }
        launch { offsetY.animateTo(translateOffset.y, animationSpec) }

    }

    fun scaleTo(scaleOffset: Offset) = with(scope) {
        launch { scaleX.animateTo(scaleOffset.x, animationSpec) }
        launch { scaleY.animateTo(scaleOffset.y, animationSpec) }
    }

    fun isAnimating(): Boolean {
        return listOf(offsetX, offsetY, scaleX, scaleY, rotation).any { it.isRunning }
    }

    fun snapToRotation(rot: Float) = with(scope) {
        launch { rotation.snapTo(rot) }
    }

    fun snapToTranslation(snapOffset: Offset) = with(scope) {
        launch { offsetX.snapTo(snapOffset.x) }
        launch { offsetY.snapTo(snapOffset.y) }
    }

}