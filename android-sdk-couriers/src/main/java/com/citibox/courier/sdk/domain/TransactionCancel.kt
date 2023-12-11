package com.citibox.courier.sdk.domain

internal enum class TransactionCancel(val code: String) {
    NOT_STARTED("not_started"),
    CANT_OPEN_BOXES("cant_open_boxes"),
    PARCEL_MISTAKEN("parcel_mistaken"),
    PACKAGE_IN_BOX("package_in_box"),
    NEED_HAND_DELIVERY("need_hand_delivery"),
    OTHER("other"),
}