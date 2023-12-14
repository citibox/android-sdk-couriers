package com.citibox.courier.sdk

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.citibox.courier.sdk.deeplink.DeepLinkRetrievalContract
import com.citibox.courier.sdk.domain.RetrievalParams
import com.citibox.courier.sdk.domain.RetrievalResult
import com.citibox.courier.sdk.domain.TransactionError

class RetrievalLauncher private constructor(
    private val onResult: (RetrievalResult) -> Unit
) {

    private lateinit var deepLinkLauncher: ActivityResultLauncher<RetrievalParams>

    constructor(fragment: Fragment, onResult: (RetrievalResult) -> Unit) :
            this(onResult) {
        deepLinkLauncher = fragment.registerForActivityResult(
            /* contract = */ DeepLinkRetrievalContract(),
            /* callback = */ onResult
        )
    }

    constructor(activity: ComponentActivity, onResult: (RetrievalResult) -> Unit) :
            this(onResult) {
        deepLinkLauncher = activity.registerForActivityResult(
            /* contract = */ DeepLinkRetrievalContract(),
            /* callback = */ onResult
        )
    }

    fun launch(params: RetrievalParams) {
        runCatching {
            deepLinkLauncher.launch(params)
        }.getOrElse {
            Log.w("RetrievalLauncher", "Error launching web view", it)
            fail()
        }
    }

    private fun fail() {
        onResult(RetrievalResult.Failure(TransactionError.LAUNCHING_PROBLEM.code))
    }
}