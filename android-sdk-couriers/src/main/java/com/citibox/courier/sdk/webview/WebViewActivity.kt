package com.citibox.courier.sdk.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.citibox.courier.sdk.domain.DeliveryParams
import com.citibox.courier.sdk.domain.TransactionCancel
import com.citibox.courier.sdk.domain.TransactionResult
import com.citibox.courier.sdk.theme.AndroidsdkcouriersTheme
import com.citibox.courier.sdk.webview.compose.CourierWebView
import com.citibox.courier.sdk.webview.models.SuccessData
import com.citibox.courier.sdk.webview.usecase.GetUrlUseCase
import java.security.MessageDigest

class WebViewActivity : ComponentActivity() {

    private val token: String
        get() = intent.getStringExtra(EXTRA_TOKEN) ?: ""

    private val tracking: String
        get() = intent.getStringExtra(EXTRA_TRACKING) ?: ""

    private val phone: String
        get() = intent.getStringExtra(EXTRA_PHONE) ?: ""

    private val phoneHashed: String
        get() = intent.getStringExtra(EXTRA_PHONE_HASHED) ?: ""

    private val dimensions: String
        get() = intent.getStringExtra(EXTRA_DIMENSIONS) ?: ""

    private val permissionsRequester =
        PermissionsRequester(
            activity = this,
            permissions = listOf(android.Manifest.permission.CAMERA),
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidsdkcouriersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CourierWebView(
                        url = url,
                        onSuccessCallback = ::onSuccess,
                        onFailCallback = ::onFail,
                        onErrorCallback = ::onError,
                        onCancelCallback = ::onCancel,
                    )
                }
            }
        }

        setDefaultResult()
        permissionsRequester.askPermissionsRationale()
    }

    private fun setDefaultResult(){
        setResult(RESULT_CANCELED, Intent().apply {
            putExtra(TransactionResult.FAILURE_CODE_KEY.code, TransactionCancel.NOT_STARTED.code)
        })
    }

    private val url: String
        get() = GetUrlUseCase().invoke(
            accessToken = token,
            tracking = tracking,
            phone = phone,
            phoneHashed = phoneHashed,
            dimensions = dimensions
        )

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

    companion object {

        const val EXTRA_TOKEN = "WebViewActivity:token"
        const val EXTRA_TRACKING = "WebViewActivity:tracking"
        const val EXTRA_PHONE = "WebViewActivity:phone"
        const val EXTRA_PHONE_HASHED = "WebViewActivity:phone_hashed"
        const val EXTRA_DIMENSIONS = "WebViewActivity:dimensions"

        const val EXTRA_BOX_NUMBER = "box_number"
        const val EXTRA_CITIBOX_ID = "citibox_id"
        const val EXTRA_DELIVERY_ID = "delivery_id"

        fun buildIntent(context: Context, input: DeliveryParams): Intent =
            Intent(context, WebViewActivity::class.java).apply {
                putExtra(EXTRA_TOKEN, input.accessToken)
                putExtra(EXTRA_TRACKING, input.tracking)
                putExtra(EXTRA_DIMENSIONS, input.dimensions?.trim().orEmpty())

                if (input.isPhoneHashed) {
                    putExtra(EXTRA_PHONE_HASHED, input.recipientPhone.calculateSHA256())
                } else {
                    putExtra(EXTRA_PHONE, input.recipientPhone)
                }
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
    }
}
