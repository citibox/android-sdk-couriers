package com.citibox.courier.sdk.webview.components

import android.webkit.PermissionRequest
import android.webkit.WebChromeClient

internal fun buildWebChromeClient() = object : WebChromeClient() {

    override fun onPermissionRequest(request: PermissionRequest?) {
        request?.grant(request.resources)
    }

}