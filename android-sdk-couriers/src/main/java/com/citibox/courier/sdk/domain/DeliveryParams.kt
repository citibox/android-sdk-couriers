package com.citibox.courier.sdk.domain

data class DeliveryParams(
    var accessToken: String,
    var tracking: String,
    var recipientPhone: String,
    var isPhoneHashed: Boolean = true,
    var dimensions: String? = null,
)