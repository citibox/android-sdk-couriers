package com.citibox.courier.sdk.domain

data class DeliveryParams(
    val accessToken: String,
    val tracking: String,
    val recipientPhone: String,
    val isPhoneHashed: Boolean = true,
    val dimensions: String? = null,
)