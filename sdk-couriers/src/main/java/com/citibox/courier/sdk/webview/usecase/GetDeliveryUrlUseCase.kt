package com.citibox.courier.sdk.webview.usecase

import android.net.Uri
import com.citibox.courier.sdk.BuildConfig
import com.citibox.courier.sdk.webview.models.WebAppEnvironment
import com.citibox.courier.sdk.webview.utils.appendPaths

class GetDeliveryUrlUseCase {

    operator fun invoke(
        environment: WebAppEnvironment,
        accessToken: String,
        tracking: String,
        phone: String,
        phoneHashed: String,
        dimensions: String,
        bookingId: String
    ): String {
        val (base, segment) = when (environment) {
            WebAppEnvironment.Production -> BuildConfig.WEBAPP_PRO_URL to
                    BuildConfig.WEBAPP_SEGMENT_DELIVERY

            WebAppEnvironment.Sandbox -> BuildConfig.WEBAPP_SANDBOX_URL to
                    BuildConfig.WEBAPP_SEGMENT_DELIVERY

            WebAppEnvironment.Test -> BuildConfig.WEBAPP_TEST_URL to
                    BuildConfig.WEBAPP_SEGMENT_DELIVERY_TEST

            WebAppEnvironment.Local -> BuildConfig.WEBAPP_LOCAL_URL to
                    BuildConfig.WEBAPP_SEGMENT_DELIVERY
        }

        val segments = segment.split("/")

        return Uri.parse(base)
            .buildUpon()
            .appendPaths(segments)
            .appendQueryParameter(PARAM_ACCESS_TOKEN, accessToken)
            .appendQueryParameter(PARAM_TRACKING, tracking)
            .appendQueryParameter(PARAM_PHONE, phone)
            .appendQueryParameter(PARAM_PHONE_HASHED, phoneHashed)
            .appendQueryParameter(PARAM_DIMENSIONS, dimensions)
            .appendQueryParameter(PARAM_BOOKING_ID, bookingId)
            .build()
            .toString()
    }

    companion object {
        private const val PARAM_ACCESS_TOKEN = "access_token"
        private const val PARAM_TRACKING = "tracking"
        private const val PARAM_PHONE = "recipient_phone"
        private const val PARAM_PHONE_HASHED = "recipient_hash"
        private const val PARAM_DIMENSIONS = "dimensions"
        private const val PARAM_BOOKING_ID = "booking_id"
    }
}