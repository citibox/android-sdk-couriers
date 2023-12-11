package com.citibox.courier.sdk.domain

internal enum class TransactionError(val code: String) {
    TRACKING_MISSING("tracking_missing"),
    ACCESS_TOKEN_MISSING("access_token_missing"),
    CITIBOX_ID_MISSING("citibox_id_missing"),
    ACCESS_TOKEN_INVALID("access_token_invalid"),
    ACCESS_TOKEN_PERMISSION_DENIED("access_token_permissions_denied"),
    RECIPIENT_PHONE_MISSING("recipient_phone_missing"),
    DUPLICATED_TRACKING("duplicated_trackings"),
    RECIPIENT_PHONE_INVALID("recipient_phone_invalid"),
    WRONG_LOCATION("wrong_location"),
    ARGUMENTS_MISSING("arguments_missing"),
    DATA_NOT_RECEIVED("data_not_received"),
    LAUNCHING_PROBLEM("launching_problem")
}
