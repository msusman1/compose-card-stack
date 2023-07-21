package com.msusman.compose.cardstack

/**
 * Created by Muhammad Usman : msusman97@gmail.com on 7/14/2023.
 */
enum class SwipeMethod {
    AUTOMATIC,
    MANUAL,
    AUTOMATIC_AND_MANUAL,
    NONE;

    fun isAutomaticSwipeAllowed(): Boolean {
        return this == AUTOMATIC || this == AUTOMATIC_AND_MANUAL
    }

    fun isManualSwipeAllowed(): Boolean {
        return this == MANUAL || this == AUTOMATIC_AND_MANUAL
    }
}