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
) = CourierJavascriptInterface(
    onSuccessCallback = onSuccessCallback,
    onFailCallback = onFailCallback,
    onErrorCallback = onErrorCallback,
    onCancelCallback = onCancelCallback
)

internal class CourierJavascriptInterface(
    private val onSuccessCallback: OnSuccessCallback,
    private val onFailCallback: OnUnSuccessCallback,
    private val onErrorCallback: OnUnSuccessCallback,
    private val onCancelCallback: OnUnSuccessCallback,
) {
    @JavascriptInterface
    fun onSuccess(
        boxNumber: Int,
        citiboxId: Int,
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
    fun onSuccess(
        boxNumber: Int,
        citiboxId: Int,
    ) {
        onSuccessCallback(
            SuccessData(
                boxNumber = boxNumber,
                citiboxId = citiboxId,
                deliveryId = ""
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