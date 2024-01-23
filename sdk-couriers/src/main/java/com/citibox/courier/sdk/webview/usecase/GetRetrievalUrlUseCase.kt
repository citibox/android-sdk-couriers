package com.citibox.courier.sdk.webview.usecase

import android.net.Uri
import com.citibox.courier.sdk.BuildConfig
import com.citibox.courier.sdk.webview.models.WebAppEnvironment
import com.citibox.courier.sdk.webview.utils.appendPaths

class GetRetrievalUrlUseCase {

    operator fun invoke(
        environment: WebAppEnvironment,
        accessToken: String,
        citiboxId: String,
    ): String {
        val (base, segment) = when (environment) {
            WebAppEnvironment.Production -> BuildConfig.WEBAPP_PRO_URL to
                    BuildConfig.WEBAPP_SEGMENT_RETRIEVAL

            WebAppEnvironment.Sandbox -> BuildConfig.WEBAPP_SANDBOX_URL to
                    BuildConfig.WEBAPP_SEGMENT_RETRIEVAL

            WebAppEnvironment.Test -> BuildConfig.WEBAPP_TEST_URL to
                    BuildConfig.WEBAPP_SEGMENT_RETRIEVAL_TEST

            WebAppEnvironment.Local -> BuildConfig.WEBAPP_LOCAL_URL to
                    BuildConfig.WEBAPP_SEGMENT_RETRIEVAL
        }

        val segments = segment.split("/")

        return Uri.parse(base)
            .buildUpon()
            .appendPaths(segments)
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