package com.msusman.compose.cardstack.sample

/**
 * Created by Muhammad Usman : msusman97@gmail.com on 7/20/2023.
 */
val item1 = Item(
    "https://images.unsplash.com/photo-1521668576204-57ae3afee860?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80",
    "Rakotzbrücke, Gablenz, Germany",
    "March 22, 2018"
)
val item2 = Item(
    "https://images.unsplash.com/photo-1454391304352-2bf4678b1a7a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80",
    "Palm tree and plant",
    "February 2, 2016"
)
val item3 = Item(
    "https://images.unsplash.com/photo-1568323993144-20d546ba585d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80",
    "Airplane flying during the sunset",
    "September 13, 2019"
)
val item4 = Item(
    "https://images.unsplash.com/photo-1614094082869-cd4e4b2905c7?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80",
    "Winter in San Martino di Castrozza, Italia",
    "February 23, 2021"
)

data class Item(
    val url: String? = null,
    val text: String = "",
    val subText: String = ""
)

val items = listOf(item1, item2, item3, item4, item1, item2, item3, item4)