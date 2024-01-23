package com.citibox.courier.sdk.domain

internal enum class TransactionFailure(val code: String) {
    PARCEL_NOT_AVAILABLE("parcel_not_available"),
    MAX_REOPENS_EXCEED("max_reopens_exceed"),
    EMPTY_BOX("empty_box"),
    BOX_NOT_AVAILABLE("box_not_available"),
    USER_BLOCKED("user_blocked"),
    USER_AUTO_CREATION_FORBIDDEN("user_autocreation_forbidden"),
    ANY_BOX_EMPTY("any_box_empty"),
}