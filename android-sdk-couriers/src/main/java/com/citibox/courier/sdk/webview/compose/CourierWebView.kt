package com.citibox.courier.sdk.webview.compose

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
@Composable
internal fun CourierWebView(
    url: String,
    onSuccess: OnSuccessCallback,
    onUnSuccessCallback: OnUnSuccessCallback
) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )

            settings.javaScriptEnabled = true
            settings.allowContentAccess = true
            settings.domStorageEnabled = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.mediaPlaybackRequiresUserGesture = true

            addJavascriptInterface(
                /* object = */ buildJavascriptInterface(
                    onSuccess = onSuccess,
                    onUnSuccess = onUnSuccessCallback
                ),
                /* name = */ "CitiboxCourierSDK"
            )
            loadUrl(url)
        }
    },
        update = {
            it.loadUrl(url)
        })
}

internal typealias OnSuccessCallback = (
    boxNumber: String,
    citiboxId: String,
    deliveryId: String
) -> Unit

internal typealias OnUnSuccessCallback = (
    code: String
) -> Unit

internal fun buildJavascriptInterface(
    onSuccess: OnSuccessCallback,
    onUnSuccess: OnUnSuccessCallback
) = object {

    @JavascriptInterface
    fun onSuccess(
        boxNumber: String,
        citiboxId: String,
        deliveryId: String
    ) {
        onSuccess(boxNumber, citiboxId, deliveryId)
    }

    @JavascriptInterface
    fun onFail(failureCode: String) {

    }

    @JavascriptInterface
    fun onError(errorCode: String) {

    }

    @JavascriptInterface
    fun onCancel(cancelCode: String) {

    }
}