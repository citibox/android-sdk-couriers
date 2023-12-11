package com.citibox.courier.sdk.webview.compose

import android.webkit.JavascriptInterface
import com.citibox.courier.sdk.webview.models.OnSuccessCallback
import com.citibox.courier.sdk.webview.models.OnUnSuccessCallback
import com.citibox.courier.sdk.webview.models.SuccessData

internal fun buildCourierJavascriptInterface(
    onSuccessCallback: OnSuccessCallback,
    onFailCallback: OnUnSuccessCallback,
    onErrorCallback: OnUnSuccessCallback,
    onCancelCallback: OnUnSuccessCallback,
) = object {

    @JavascriptInterface
    fun onSuccess(
        boxNumber: String,
        citiboxId: String,
        deliveryId: String
    ) {
        onSuccessCallback(
            SuccessData(
            boxNumber = boxNumber,
            citiboxId = citiboxId,
            deliveryId = deliveryId
        )
        )
    }

    @JavascriptInterface
    fun onFail(failureCode: String) {
        onFailCallback(failureCode)
    }

    @JavascriptInterface
    fun onError(errorCode: String) {
        onErrorCallback(errorCode)
    }

    @JavascriptInterface
    fun onCancel(cancelCode: String) {
        onCancelCallback(cancelCode)
    }
}