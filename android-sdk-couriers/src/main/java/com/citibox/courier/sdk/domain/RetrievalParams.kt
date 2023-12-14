package com.citibox.courier.sdk.domain

import com.citibox.courier.sdk.webview.models.WebAppEnvironment

data class RetrievalParams(
    val accessToken: String,
    val citiboxId: String,
    val webAppEnvironment: WebAppEnvironment
)
