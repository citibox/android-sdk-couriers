package com.citibox.courier.sdk.example.models

data class MainState(
    val token: String = "",
    val tracking: String = "",
    val phone: String = "",
    val phoneHashed: Boolean = false,
    val dimensions: String = "",
    val resultMessage: String = ""
)
