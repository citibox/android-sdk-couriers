package com.citibox.courier.sdk.webview.usecase

import androidx.core.net.toUri
import com.citibox.courier.sdk.BuildConfig
import com.citibox.courier.sdk.webview.models.WebAppEnvironment

class GetRetrievalUrlUseCase {

    operator fun invoke(
        environment: WebAppEnvironment,
        accessToken: String,
        citiboxId: String,
    ): String {
        val (base, segment) = when (environment) {
            WebAppEnvironment.Production -> BuildConfig.WEBAPP_PRO_URL to
                    BuildConfig.WEBAPP_SEGMENT_DELIVERY

            WebAppEnvironment.Sandbox -> BuildConfig.WEBAPP_SANDBOX_URL to
                    BuildConfig.WEBAPP_SEGMENT_DELIVERY

            WebAppEnvironment.Test -> BuildConfig.WEBAPP_TEST_URL to
                    BuildConfig.WEBAPP_SEGMENT_TEST

            WebAppEnvironment.Local -> BuildConfig.WEBAPP_LOCAL_URL to
                    BuildConfig.WEBAPP_SEGMENT_DELIVERY
        }

        return base.toUri()
            .buildUpon()
            .appendPath(segment)
            .appendQueryParameter(PARAM_ACCESS_TOKEN, accessToken)
            .appendQueryParameter(PARAM_CITIBOX_ID, citiboxId)
            .build()
            .toString()
    }

    companion object {
        private const val PARAM_ACCESS_TOKEN = "access_token"
        private const val PARAM_CITIBOX_ID = "citibox_id"
    }
}