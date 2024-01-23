package com.citibox.courier.sdk.webview.utils

import android.net.Uri

fun Uri.Builder.appendPaths(list: List<String>): Uri.Builder{
    list.forEach {
        this.appendPath(it)
    }
    return this
}