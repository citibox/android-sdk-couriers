package com.citibox.courier.sdk.example.delivery.models

import com.citibox.courier.sdk.webview.models.WebAppEnvironment

data class DeliveryState(
    val token: String = "",
    val tracking: String = "",
    val phone: String = "",
    val phoneHashed: Boolean = false,
    val dimensions: String = "",
    val bookingId: String = "",
    val resultMessage: String = "",
    val environment: WebAppEnvironment = WebAppEnvironment.Sandbox
)
