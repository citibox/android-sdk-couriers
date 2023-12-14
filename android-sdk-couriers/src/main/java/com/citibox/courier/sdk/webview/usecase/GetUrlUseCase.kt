package com.citibox.courier.sdk.webview.usecase

import androidx.core.net.toUri
import com.citibox.courier.sdk.BuildConfig
import com.citibox.courier.sdk.webview.models.WebAppEnvironment

class GetUrlUseCase {

    operator fun invoke(
        environment: WebAppEnvironment,
        accessToken: String,
        tracking: String,
        phone: String,
        phoneHashed: String,
        dimensions: String
    ): String {
        val base = when(environment){
            WebAppEnvironment.Production -> BuildConfig.WEBAPP_PRO_URL
            WebAppEnvironment.Sandbox -> BuildConfig.WEBAPP_SANDBOX_URL
            WebAppEnvironment.Test -> BuildConfig.WEBAPP_TEST_URL
        }

        return base.toUri()
            .buildUpon()
            .appendQueryParameter(PARAM_ACCESS_TOKEN, accessToken)
            .appendQueryParameter(PARAM_TRACKING, tracking)
            .appendQueryParameter(PARAM_PHONE, phone)
            .appendQueryParameter(PARAM_PHONE_HASHED, phoneHashed)
            .appendQueryParameter(PARAM_DIMENSIONS, dimensions)
            .build()
            .toString()
    }

    companion object {
        private const val PARAM_ACCESS_TOKEN = "access_token"
        private const val PARAM_TRACKING = "tracking"
        private const val PARAM_PHONE = "recipient_phone"
        private const val PARAM_PHONE_HASHED = "recipient_hash"
        private const val PARAM_DIMENSIONS = "dimensions"
    }
}