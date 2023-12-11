package com.citibox.courier.sdk.example.models

import com.citibox.courier.sdk.domain.DeliveryParams

sealed class MainSideEffect {

    data class Launch(val params: DeliveryParams) : MainSideEffect()

}