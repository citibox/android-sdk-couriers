package com.citibox.courier.sdk.webview

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.citibox.courier.sdk.domain.RetrievalParams
import com.citibox.courier.sdk.domain.RetrievalResult
import com.citibox.courier.sdk.domain.TransactionCancel
import com.citibox.courier.sdk.domain.TransactionResult

internal class WebViewRetrievalContract :
    ActivityResultContract<RetrievalParams, RetrievalResult>() {
    override fun createIntent(
        context: Context,
        input: RetrievalParams,
    ): Intent {
        return WebViewActivity.buildIntent(
            context,
            input,
        )
    }

    override fun parseResult(resultCode: Int, intent: Intent?): RetrievalResult =
        when (resultCode) {
            Activity.RESULT_OK -> {
                val validIntent = requireNotNull(intent) {
                    "Intent mus not be null when result is OK"
                }
                val boxNumber =
                    validIntent.getIntExtra(WebViewActivity.EXTRA_BOX_NUMBER, Int.MIN_VALUE)

                val citiboxId =
                    validIntent.getIntExtra(WebViewActivity.EXTRA_CITIBOX_ID, Int.MIN_VALUE)

                RetrievalResult.Success(
                    boxNumber = boxNumber,
                    citiboxId = citiboxId,
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

                    else -> RetrievalResult.Error(TransactionCancel.OTHER.code)
                }
        }

    private fun parseError(intent: Intent): RetrievalResult {
        val typeError =
            requireNotNull(intent.getStringExtra(TransactionResult.ERROR_CODE_KEY.code)) {
                "Missing type error"
            }
        return RetrievalResult.Error(typeError)
    }

    private fun parseCancel(intent: Intent): RetrievalResult {
        val typeError =
            requireNotNull(intent.getStringExtra(TransactionResult.CANCEL_CODE_KEY.code)) {
                "Missing type cancellation"
            }
        return RetrievalResult.Cancel(typeError)
    }

    private fun parseFailure(intent: Intent): RetrievalResult {
        val typeError =
            requireNotNull(intent.getStringExtra(TransactionResult.FAILURE_CODE_KEY.code)) {
                "Missing type failure"
            }
        return RetrievalResult.Failure(typeError)
    }

}