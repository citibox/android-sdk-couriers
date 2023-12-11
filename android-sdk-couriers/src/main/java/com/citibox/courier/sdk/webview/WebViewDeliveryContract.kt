package com.citibox.courier.sdk.webview

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.citibox.courier.sdk.domain.DeliveryParams
import com.citibox.courier.sdk.domain.DeliveryResult
import com.citibox.courier.sdk.domain.TransactionCancel
import com.citibox.courier.sdk.domain.TransactionResult

internal class WebViewDeliveryContract :
    ActivityResultContract<DeliveryParams, DeliveryResult>() {
    override fun createIntent(context: Context, input: DeliveryParams): Intent {
        return WebViewActivity.buildIntent(
            context,
            input
        )
    }

    override fun parseResult(resultCode: Int, intent: Intent?): DeliveryResult =
        when (resultCode) {
            Activity.RESULT_OK -> {
                val validIntent = requireNotNull(intent) {
                    "Intent mus not be null when result is OK"
                }
                val boxNumber =
                    requireNotNull(validIntent.extras?.getInt(WebViewActivity.EXTRA_BOX_NUMBER)) {
                        "Missing box number"
                    }
                val citiboxId =
                    validIntent.getStringExtra(WebViewActivity.EXTRA_CITIBOX_ID).orEmpty()
                val deliveryId =
                    validIntent.getStringExtra(WebViewActivity.EXTRA_DELIVERY_ID).orEmpty()
                DeliveryResult.Success(
                    boxNumber = boxNumber,
                    citiboxId = citiboxId,
                    deliveryId = deliveryId,
                )
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

}