package com.citibox.courier.sdk.webview.compose

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.citibox.courier.sdk.webview.models.OnSuccessCallback
import com.citibox.courier.sdk.webview.models.OnUnSuccessCallback

@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
@Composable
internal fun CourierWebView(
    url: String,
    onSuccessCallback: OnSuccessCallback,
    onFailCallback: OnUnSuccessCallback,
    onErrorCallback: OnUnSuccessCallback,
    onCancelCallback: OnUnSuccessCallback,
) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )

            webViewClient = buildCourierWebViewClient(
                onSuccessCallback = onSuccessCallback,
                onFailCallback = onFailCallback,
                onErrorCallback = onErrorCallback,
                onCancelCallback = onCancelCallback
            )

            webChromeClient = buildWebChromeClient()

            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.allowContentAccess = true
            settings.domStorageEnabled = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.mediaPlaybackRequiresUserGesture = false
            settings.databaseEnabled = true

            addJavascriptInterface(
                /* object = */ buildCourierJavascriptInterface(
                    onSuccessCallback = onSuccessCallback,
                    onFailCallback = onFailCallback,
                    onErrorCallback = onErrorCallback,
                    onCancelCallback = onCancelCallback
                ),
                /* name = */ "CitiboxCourierSDK"
            )

            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)

            loadUrl(url)
        }
    },
        update = {
            it.loadUrl(url)
        })
}
