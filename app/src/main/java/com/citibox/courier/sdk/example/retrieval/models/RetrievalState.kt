package com.citibox.courier.sdk.example.retrieval.models

import com.citibox.courier.sdk.webview.models.WebAppEnvironment

data class RetrievalState(
    val token: String = "",
    val citiboxId: String = "",
    val resultMessage: String = "",
    val environment: WebAppEnvironment = WebAppEnvironment.Sandbox,
)
