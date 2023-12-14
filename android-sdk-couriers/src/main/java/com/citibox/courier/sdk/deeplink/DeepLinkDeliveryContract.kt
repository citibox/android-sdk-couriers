package com.citibox.courier.sdk.deeplink

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.citibox.courier.sdk.domain.DeliveryParams
import com.citibox.courier.sdk.domain.DeliveryResult
import com.citibox.courier.sdk.domain.TransactionCancel
import com.citibox.courier.sdk.domain.TransactionError
import com.citibox.courier.sdk.domain.TransactionResult
import java.security.MessageDigest

internal class DeepLinkDeliveryContract :
    ActivityResultContract<DeliveryParams, DeliveryResult>() {
    override fun createIntent(context: Context, input: DeliveryParams): Intent {
        val uriBuilder = Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(PATH)
            .appendQueryParameter(QUERY_ACCESS_TOKEN, input.accessToken.trim())
            .appendQueryParameter(QUERY_TRACKING, input.tracking.trim())
            .appendQueryParameter(QUERY_DIMENSIONS, input.dimensions?.trim().orEmpty())

        if (input.isPhoneHashed) {
            uriBuilder.appendQueryParameter(QUERY_HASH, input.recipientPhone.calculateSHA256())
        } else {
            uriBuilder.appendQueryParameter(QUERY_PHONE, input.recipientPhone)
        }

        return Intent(Intent.ACTION_VIEW).setData(uriBuilder.build())
    }

    override fun parseResult(resultCode: Int, intent: Intent?): DeliveryResult =
        when (resultCode) {
            Activity.RESULT_OK -> runCatching {
                val validIntent = requireNotNull(intent) {
                    "Intent mus not be null when result is OK"
                }
                val boxNumber = requireNotNull(validIntent.extras?.getInt(EXTRA_BOX_NUMBER)) {
                    "Missing box number"
                }
                val citiboxId = requireNotNull(validIntent.extras?.getInt(EXTRA_CITIBOX_ID)) {
                    "Missing Citibox ID"
                }
                val deliveryId = validIntent.getStringExtra(EXTRA_DELIVERY_ID).orEmpty()
                DeliveryResult.Success(
                    boxNumber = boxNumber,
                    citiboxId = citiboxId,
                    deliveryId = deliveryId,
                )
            }.getOrElse {
                DeliveryResult.Error(TransactionError.DATA_NOT_RECEIVED.code)
            }

            else ->
                when {
                    intent?.hasExtra(TransactionResult.ERROR_CODE_KEY.code) == true ->
                        parseError(intent = intent)

                    intent?.hasExtra(TransactionResult.CANCEL_CODE_KEY.code) == true ->
                        parseCancel(intent = intent)

                    intent?.hasExtra(TransactionResult.FAILURE_CODE_KEY.code) == true ->
                        parseFailure(intent = intent)

                    else -> DeliveryResult.Error(TransactionCancel.OTHER.code)
                }
        }

    private fun parseError(intent: Intent): DeliveryResult {
        val typeError =
            requireNotNull(intent.getStringExtra(TransactionResult.ERROR_CODE_KEY.code)) {
                "Missing type error"
            }
        return DeliveryResult.Error(typeError)
    }

    private fun parseCancel(intent: Intent): DeliveryResult {
        val typeError =
            requireNotNull(intent.getStringExtra(TransactionResult.CANCEL_CODE_KEY.code)) {
                "Missing type cancellation"
            }
        return DeliveryResult.Cancel(typeError)
    }

    private fun parseFailure(intent: Intent): DeliveryResult {
        val typeError =
            requireNotNull(intent.getStringExtra(TransactionResult.FAILURE_CODE_KEY.code)) {
                "Missing type failure"
            }
        return DeliveryResult.Failure(typeError)
    }

    private fun String.calculateSHA256(): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val data = digest.digest(this.toByteArray())

        return bin2hex(data)
    }

    private fun bin2hex(data: ByteArray): String {
        val hex = StringBuilder(data.size * 2)
        for (b in data) hex.append(String.format("%02x", b.toInt() and 0xFF))
        return hex.toString()
    }

    companion object {
        private const val SCHEME = "app"
        private const val AUTHORITY = "couriers.citibox.com"
        private const val PATH = "transaction"
        private const val QUERY_ACCESS_TOKEN = "access_token"
        private const val QUERY_TRACKING = "tracking"
        private const val QUERY_PHONE = "recipient_phone"
        private const val QUERY_HASH = "recipient_hash"
        private const val QUERY_DIMENSIONS = "dimensions"

        private const val EXTRA_BOX_NUMBER = "box_number"
        private const val EXTRA_CITIBOX_ID = "citibox_id"
        private const val EXTRA_DELIVERY_ID = "delivery_id"
    }
}