package com.citibox.courier.sdk.webview.components

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.FrameLayout
import com.citibox.courier.sdk.R
import com.citibox.courier.sdk.webview.models.OnSuccessCallback
import com.citibox.courier.sdk.webview.models.OnUnSuccessCallback

@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
class CourierWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var url: String = ""
        set(value) {
            field = value
            webView.loadUrl(value)
        }

    private val webView: WebView
    private val loading: View

    init {
        inflate(context, R.layout.view_courier_webview, this)

        webView = findViewById(R.id.webView)
        loading = findViewById(R.id.progressBar)

        initWebView()
    }

    private fun initWebView() {
        webView.apply {
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            webChromeClient = buildWebChromeClient()

            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.allowContentAccess = true
            settings.domStorageEnabled = true
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.mediaPlaybackRequiresUserGesture = false
            settings.databaseEnabled = true

            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
        }
    }

    fun callbacks(
        onSuccessCallback: OnSuccessCallback,
        onFailCallback: OnUnSuccessCallback,
        onErrorCallback: OnUnSuccessCallback,
        onCancelCallback: OnUnSuccessCallback
    ) {
        webView.webViewClient = buildCourierWebViewClient(
            onSuccessCallback,
            onFailCallback,
            onErrorCallback,
            onCancelCallback,
            onLoading = {
                loading.visibility = it.toVisibility()
            }
        )

        webView.addJavascriptInterface(
            /* object = */ buildCourierJavascriptInterface(
                onSuccessCallback = onSuccessCallback,
                onFailCallback = onFailCallback,
                onErrorCallback = onErrorCallback,
                onCancelCallback = onCancelCallback
            ),
            /* name = */ "CitiboxCourierSDK"
        )
    }

    fun release() {
        webView.apply {
            clearCache(false)
            loadUrl("about:blank")
            onPause()
            removeAllViews()
            pauseTimers()
            destroy()
        }
    }

    private fun Boolean.toVisibility() = when (this) {
        true -> View.VISIBLE
        false -> View.GONE
    }
}
