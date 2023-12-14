package com.citibox.courier.sdk.example.models

import com.citibox.courier.sdk.webview.models.WebAppEnvironment

data class MainState(
    val token: String = "",
    val tracking: String = "",
    val phone: String = "",
    val phoneHashed: Boolean = false,
    val dimensions: String = "",
    val resultMessage: String = "",
    val environment: WebAppEnvironment = WebAppEnvironment.Sandbox
)
