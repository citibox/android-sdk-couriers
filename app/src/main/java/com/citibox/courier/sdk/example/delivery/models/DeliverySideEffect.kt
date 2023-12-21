package com.citibox.courier.sdk.example.delivery.models

import com.citibox.courier.sdk.domain.DeliveryParams

sealed class DeliverySideEffect {

    data class Launch(val params: DeliveryParams) : DeliverySideEffect()

}