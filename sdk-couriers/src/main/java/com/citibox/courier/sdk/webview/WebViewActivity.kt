package com.citibox.courier.sdk.webview

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.citibox.courier.sdk.R
import com.citibox.courier.sdk.domain.DeliveryParams
import com.citibox.courier.sdk.domain.RetrievalParams
import com.citibox.courier.sdk.domain.TransactionCancel
import com.citibox.courier.sdk.domain.TransactionResult
import com.citibox.courier.sdk.webview.components.CourierWebView
import com.citibox.courier.sdk.webview.models.SuccessData
import com.citibox.courier.sdk.webview.models.WebAppEnvironment
import com.citibox.courier.sdk.webview.usecase.GetDeliveryUrlUseCase
import com.citibox.courier.sdk.webview.usecase.GetRetrievalUrlUseCase
import java.security.MessageDigest

@Suppress("DEPRECATION")
class WebViewActivity : ComponentActivity(
    R.layout.activity_webview
) {

    private val permissionsRequester =
        PermissionsRequester(
            activity = this,
            permissions = listOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO
            ),
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<CourierWebView>(R.id.courierWebView).apply {
            callbacks(::onSuccess, ::onFail, ::onError, ::onCancel)
            url = generateInitialUrl()
        }

        setDefaultResult()
        permissionsRequester.askPermissionsRationale()
    }

    private fun generateInitialUrl(): String =
        if (intent.isDelivery()) {
            val params = intent.deliveryParams()
            GetDeliveryUrlUseCase().invoke(
                environment = params.webAppEnvironment,
                accessToken = params.accessToken,
                tracking = params.tracking,
                phone = if (!params.isPhoneHashed) params.recipientPhone else "",
                phoneHashed = if (params.isPhoneHashed) params.recipientPhone else "",
                dimensions = params.dimensions.orEmpty(),
                bookingId = params.bookingId.orEmpty()
            )
        } else {
            val params = intent.retrievalParams()
            GetRetrievalUrlUseCase().invoke(
                environment = params.webAppEnvironment,
                accessToken = params.accessToken,
                citiboxId = params.citiboxId
            )
        }


    private fun setDefaultResult() {
        setResult(RESULT_CANCELED, Intent().apply {
            putExtra(TransactionResult.FAILURE_CODE_KEY.code, TransactionCancel.NOT_STARTED.code)
        })
    }

    private fun onSuccess(data: SuccessData) {
        val intent = Intent().apply {
            putExtra(EXTRA_BOX_NUMBER, data.boxNumber)
            putExtra(EXTRA_CITIBOX_ID, data.citiboxId)
            putExtra(EXTRA_DELIVERY_ID, data.deliveryId)
        }

        setResult(RESULT_OK, intent)
        finish()
    }

    private fun onFail(code: String) {
        val intent = Intent().apply {
            putExtra(TransactionResult.FAILURE_CODE_KEY.code, code)
        }
        setResult(RESULT_CANCELED, intent)
        finish()
    }

    private fun onError(code: String) {
        val intent = Intent().apply {
            putExtra(TransactionResult.ERROR_CODE_KEY.code, code)
        }
        setResult(RESULT_CANCELED, intent)
        finish()
    }

    private fun onCancel(code: String) {
        val intent = Intent().apply {
            putExtra(TransactionResult.CANCEL_CODE_KEY.code, code)
        }
        setResult(RESULT_CANCELED, intent)
        finish()
    }

    override fun onDestroy() {
        findViewById<CourierWebView>(R.id.courierWebView)?.release()
        super.onDestroy()
    }

    companion object {

        const val EXTRA_TOKEN = "WebViewActivity:token"
        const val EXTRA_TRACKING = "WebViewActivity:tracking"
        const val EXTRA_PHONE = "WebViewActivity:phone"
        const val EXTRA_PHONE_HASHED = "WebViewActivity:phone_hashed"
        const val EXTRA_DIMENSIONS = "WebViewActivity:dimensions"
        const val EXTRA_ENVIRONMENT = "WebViewActivity:environment"
        const val EXTRA_BOOKING_ID = "WebViewActivity:booking_id"

        const val EXTRA_BOX_NUMBER = "box_number"
        const val EXTRA_CITIBOX_ID = "citibox_id"
        const val EXTRA_DELIVERY_ID = "delivery_id"

        fun buildIntent(
            context: Context,
            input: DeliveryParams,
        ): Intent =
            Intent(context, WebViewActivity::class.java).apply {
                putExtra(EXTRA_TOKEN, input.accessToken)
                putExtra(EXTRA_TRACKING, input.tracking)
                putExtra(EXTRA_DIMENSIONS, input.dimensions?.trim().orEmpty())
                putExtra(EXTRA_BOOKING_ID, input.bookingId?.trim().orEmpty())

                if (input.isPhoneHashed) {
                    putExtra(EXTRA_PHONE_HASHED, input.recipientPhone.calculateSHA256())
                } else {
                    putExtra(EXTRA_PHONE, input.recipientPhone)
                }

                putExtra(EXTRA_ENVIRONMENT, input.webAppEnvironment)
            }

        fun buildIntent(
            context: Context,
            input: RetrievalParams,
        ): Intent =
            Intent(context, WebViewActivity::class.java).apply {
                putExtra(EXTRA_TOKEN, input.accessToken)
                putExtra(EXTRA_CITIBOX_ID, input.citiboxId)
                putExtra(EXTRA_ENVIRONMENT, input.webAppEnvironment)
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

        private fun Intent.isDelivery(): Boolean =
            hasExtra(EXTRA_TRACKING) && (hasExtra(EXTRA_PHONE_HASHED) || hasExtra(EXTRA_PHONE))

        private fun Intent.deliveryParams() = DeliveryParams(
            accessToken = getStringExtra(EXTRA_TOKEN).orEmpty(),
            tracking = getStringExtra(EXTRA_TRACKING).orEmpty(),
            dimensions = getStringExtra(EXTRA_DIMENSIONS),
            recipientPhone = getStringExtra(EXTRA_PHONE).orEmpty()
                    + getStringExtra(EXTRA_PHONE_HASHED).orEmpty(),
            isPhoneHashed = getStringExtra(EXTRA_PHONE).isNullOrEmpty(),
            bookingId = getStringExtra(EXTRA_BOOKING_ID).orEmpty(),
            webAppEnvironment = webAppEnvironment()
        )

        private fun Intent.retrievalParams() = RetrievalParams(
            accessToken = getStringExtra(EXTRA_TOKEN).orEmpty(),
            citiboxId = getStringExtra(EXTRA_CITIBOX_ID).orEmpty(),
            webAppEnvironment = webAppEnvironment()
        )

        private fun Intent.webAppEnvironment() =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getSerializableExtra(
                    EXTRA_ENVIRONMENT,
                    WebAppEnvironment::class.java
                ) ?: WebAppEnvironment.Production
            } else {
                getSerializableExtra(EXTRA_ENVIRONMENT) as WebAppEnvironment
            }
    }
}
