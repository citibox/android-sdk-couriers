package com.citibox.courier.sdk.webview.compose

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    var isLoading by remember {
        mutableStateOf(true)
    }

    Box {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    setLayerType(View.LAYER_TYPE_SOFTWARE, null)

                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )

                    webViewClient = buildCourierWebViewClient(
                        onSuccessCallback = onSuccessCallback,
                        onFailCallback = onFailCallback,
                        onErrorCallback = onErrorCallback,
                        onCancelCallback = onCancelCallback,
                        onLoading = { loading ->
                            isLoading = loading
                        }
                    )

                    webChromeClient = buildWebChromeClient()

                    settings.javaScriptEnabled = true
                    settings.javaScriptCanOpenWindowsAutomatically = false
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
            }
        )

        if (isLoading) {
            Loading(modifier = Modifier.align(Alignment.Center))
        }
    }
}
