package com.citibox.courier.sdk.example.retrieval.models

import com.citibox.courier.sdk.domain.RetrievalParams

sealed class RetrievalSideEffect {

    data class Launch(val params: RetrievalParams): RetrievalSideEffect()

}