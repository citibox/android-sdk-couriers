package com.citibox.courier.sdk.domain

sealed class RetrievalResult {
    class Success(val boxNumber: Int, val citiboxId: Int) :
        RetrievalResult()

    class Error(val errorCode: String) : RetrievalResult()
    class Failure(val type: String) : RetrievalResult()
    class Cancel(val type: String) : RetrievalResult()
}