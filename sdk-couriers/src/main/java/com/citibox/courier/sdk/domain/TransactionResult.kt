package com.citibox.courier.sdk.domain

internal enum class TransactionResult(val code: String) {
    FAILURE_CODE_KEY("failure_code"),
    CANCEL_CODE_KEY("cancel_code"),
    ERROR_CODE_KEY("error_code"),
}