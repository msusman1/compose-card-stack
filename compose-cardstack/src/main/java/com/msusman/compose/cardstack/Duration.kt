package com.msusman.compose.cardstack

class Duration private constructor(val duration: Int) {

    companion object {
        val FAST = Duration(500)
        val NORMAL = Duration(1000)
        val SLOW = Duration(1500)
        fun fromMills(mills: Int): Duration {
            return Duration(mills)
        }
    }
}