package com.citibox.courier.sdk.webview.components

import android.net.Uri
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.citibox.courier.sdk.domain.TransactionError
import com.citibox.courier.sdk.webview.models.OnSuccessCallback
import com.citibox.courier.sdk.webview.models.OnUnSuccessCallback
import com.citibox.courier.sdk.webview.models.SuccessData

private const val PATH_SUCCESS = "/finish/success"
private const val PATH_ERROR = "/finish/error"
private const val PATH_FAIL = "/finish/fail"
private const val PATH_CANCEL = "/finish/cancel"

private const val QUERY_BOX_NUMBER = "boxNumber"
private const val QUERY_CITIBOX_ID = "citiboxId"
private const val QUERY_DELIVERY_ID = "deliveryId"
private const val QUERY_CODE = "code"

internal fun buildCourierWebViewClient(
    onSuccessCallback: OnSuccessCallback,
    onFailCallback: OnUnSuccessCallback,
    onErrorCallback: OnUnSuccessCallback,
    onCancelCallback: OnUnSuccessCallback,
    onLoading: (Boolean) -> Unit,
) = CourierWebViewClient(
    onSuccessCallback = onSuccessCallback,
    onFailCallback = onFailCallback,
    onErrorCallback = onErrorCallback,
    onCancelCallback = onCancelCallback,
    onLoading = onLoading,
)

internal class CourierWebViewClient(
    private val onSuccessCallback: OnSuccessCallback,
    private val onFailCallback: OnUnSuccessCallback,
    private val onErrorCallback: OnUnSuccessCallback,
    private val onCancelCallback: OnUnSuccessCallback,
    private val onLoading: (Boolean) -> Unit,
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?,
    ): Boolean =
        when {
            request?.url?.isSuccess() == true -> {
                request.url?.extractSuccessData()?.let(onSuccessCallback)
                    ?: onErrorCallback(TransactionError.DATA_NOT_RECEIVED.code)
                true
            }

            request?.url?.isFail() == true -> {
                request.url?.extractFailData()?.let(onFailCallback)
                    ?: onErrorCallback(TransactionError.DATA_NOT_RECEIVED.code)
                true
            }

            request?.url?.isError() == true -> {
                request.url?.extractErrorData()?.let(onErrorCallback)
                    ?: onErrorCallback(TransactionError.DATA_NOT_RECEIVED.code)
                true
            }

            request?.url?.isCancel() == true -> {
                request.url?.extractCancelData()?.let(onCancelCallback)
                    ?: onErrorCallback(TransactionError.DATA_NOT_RECEIVED.code)
                true
            }

            else -> {
                onLoading(true)
                super.shouldOverrideUrlLoading(view, request)
            }
        }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        onLoading(false)
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        onErrorCallback(TransactionError.LAUNCHING_PROBLEM.code)
    }

    private fun Uri?.isSuccess(): Boolean = this?.path?.contains(PATH_SUCCESS) ?: false

    private fun Uri?.extractSuccessData(): SuccessData? {
        val boxNumber = this?.getQueryParameter(QUERY_BOX_NUMBER)?.toIntOrNull()
        val citiboxId = this?.getQueryParameter(QUERY_CITIBOX_ID)?.toIntOrNull()
        val deliveryId = this?.getQueryParameter(QUERY_DELIVERY_ID).orEmpty()

        return if (boxNumber != null && citiboxId != null) {
            SuccessData(boxNumber, citiboxId, deliveryId)
        } else {
            null
        }
    }

    private fun Uri?.isFail(): Boolean = this?.path?.contains(PATH_FAIL) ?: false

    private fun Uri?.extractFailData(): String? = this?.getQueryParameter(QUERY_CODE)

    private fun Uri?.isError(): Boolean = this?.path?.contains(PATH_ERROR) ?: false

    private fun Uri?.extractErrorData(): String? = this?.getQueryParameter(QUERY_CODE)

    private fun Uri?.isCancel(): Boolean = this?.path?.contains(PATH_CANCEL) ?: false

    private fun Uri?.extractCancelData(): String? = this?.getQueryParameter(QUERY_CODE)
}