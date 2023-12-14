package com.citibox.courier.sdk.domain

sealed class DeliveryResult {
    class Success(val boxNumber: Int, val citiboxId: Int, val deliveryId: String) :
        DeliveryResult()

    class Error(val errorCode: String) : DeliveryResult()
    class Failure(val type: String) : DeliveryResult()
    class Cancel(val type: String) : DeliveryResult()
}