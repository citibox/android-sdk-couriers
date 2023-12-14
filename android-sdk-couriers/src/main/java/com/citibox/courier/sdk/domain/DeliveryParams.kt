package com.citibox.courier.sdk.domain

import com.citibox.courier.sdk.webview.models.WebAppEnvironment

data class DeliveryParams(
    val accessToken: String,
    val tracking: String,
    val recipientPhone: String,
    val isPhoneHashed: Boolean = true,
    val dimensions: String? = null,
    val webAppEnvironment: WebAppEnvironment
)