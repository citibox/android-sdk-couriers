package com.citibox.courier.sdk.deeplink

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.citibox.courier.sdk.domain.RetrievalParams
import com.citibox.courier.sdk.domain.RetrievalResult
import com.citibox.courier.sdk.domain.TransactionCancel
import com.citibox.courier.sdk.domain.TransactionError
import com.citibox.courier.sdk.domain.TransactionResult

internal class DeepLinkRetrievalContract :
    ActivityResultContract<RetrievalParams, RetrievalResult>() {
    override fun createIntent(context: Context, input: RetrievalParams): Intent {
        val uriBuilder = Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(PATH)
            .appendQueryParameter(QUERY_ACCESS_TOKEN, input.accessToken.trim())
            .appendQueryParameter(QUERY_CITIBOX_ID, input.citiboxId.trim())

        return Intent(Intent.ACTION_VIEW).setData(uriBuilder.build())
    }

    override fun parseResult(resultCode: Int, intent: Intent?): RetrievalResult =
        when (resultCode) {
            Activity.RESULT_OK -> runCatching {
                val validIntent = requireNotNull(intent) {
                    "Intent mus not be null when result is OK"
                }
                val boxNumber = requireNotNull(validIntent.extras?.getInt(EXTRA_BOX_NUMBER)) {
                    "Missing box number"
                }
                val citiboxId =
                    requireNotNull(validIntent.extras?.getString(EXTRA_CITIBOX_ID)?.toInt()) {
                        "Missing Citibox ID"
                    }
                RetrievalResult.Success(
                    boxNumber = boxNumber,
                    citiboxId = citiboxId,
                )
            }.getOrElse {
                RetrievalResult.Error(TransactionError.DATA_NOT_RECEIVED.code)
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

    companion object {
        private const val SCHEME = "app"
        private const val AUTHORITY = "couriers.citibox.com"
        private const val PATH = "transaction"
        private const val QUERY_ACCESS_TOKEN = "access_token"
        private const val QUERY_CITIBOX_ID = "citibox_id"

        private const val EXTRA_BOX_NUMBER = "box_number"
        private const val EXTRA_CITIBOX_ID = "citibox_id"
    }
}