package com.citibox.courier.sdk

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.citibox.courier.sdk.deeplink.DeepLinkDeliveryContract
import com.citibox.courier.sdk.webview.WebViewDeliveryContract
import com.citibox.courier.sdk.domain.DeliveryParams
import com.citibox.courier.sdk.domain.DeliveryResult
import com.citibox.courier.sdk.domain.TransactionError

class DeliveryLauncher private constructor(
    private val onResult: (DeliveryResult) -> Unit
) {

    private lateinit var deepLinkLauncher: ActivityResultLauncher<DeliveryParams>
    private lateinit var webViewLauncher: ActivityResultLauncher<DeliveryParams>

    constructor(fragment: Fragment, onResult: (DeliveryResult) -> Unit) :
            this(onResult) {
        deepLinkLauncher = fragment.registerForActivityResult(
            /* contract = */ DeepLinkDeliveryContract(),
            /* callback = */ onResult
        )
        webViewLauncher = fragment.registerForActivityResult(
            /* contract = */ WebViewDeliveryContract(),
            /* callback = */ onResult
        )
    }

    constructor(activity: ComponentActivity, onResult: (DeliveryResult) -> Unit) :
            this(onResult) {
        deepLinkLauncher = activity.registerForActivityResult(
            /* contract = */ DeepLinkDeliveryContract(),
            /* callback = */ onResult
        )
        webViewLauncher = activity.registerForActivityResult(
            /* contract = */ WebViewDeliveryContract(),
            /* callback = */ onResult
        )
    }

    fun launch(params: DeliveryParams) {
        runCatching {
            deepLinkLauncher.launch(params)
        }.getOrElse {
            Log.w("DeliveryLauncher", "Error launching deep link", it)
            launchWebView(params)
        }
    }

    private fun launchWebView(params: DeliveryParams) {
        runCatching {
            webViewLauncher.launch(params)
        }.getOrElse {
            Log.w("DeliveryLauncher", "Error launching web view", it)
            fail()
        }
    }

    private fun fail() {
        onResult(DeliveryResult.Failure(TransactionError.LAUNCHING_PROBLEM.code))
    }
}