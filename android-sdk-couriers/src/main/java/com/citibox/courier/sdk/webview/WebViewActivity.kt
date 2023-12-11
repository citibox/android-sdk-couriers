package com.citibox.courier.sdk.webview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.citibox.courier.sdk.theme.AndroidsdkcouriersTheme
import com.citibox.courier.sdk.webview.compose.CourierWebView

class WebViewActivity : ComponentActivity() {

    private val courierUrl: String
        get() {
            // Inject or replace params when needed
            return URL
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidsdkcouriersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CourierWebView(
                        url = courierUrl,
                        onSuccess = { boxNumber, citiboxId, deliveryId ->
                            // TODO bounce it back
                        },
                        onUnSuccessCallback = {
                            // TODO bounce it back
                        })
                }
            }
        }
    }

    companion object {
        private const val URL = "https://couriers.citibox.com/"

        const val EXTRA_TOKEN = "WebViewActivity:token"
        const val EXTRA_TRACKING = "WebViewActivity:tracking"
        const val EXTRA_PHONE = "WebViewActivity:phone"
        const val EXTRA_PHONE_HASHED = "WebViewActivity:phone_hashed"
        const val EXTRA_DIMENSIONS = "WebViewActivity:dimensions"

        const val EXTRA_BOX_NUMBER = "box_number"
        const val EXTRA_CITIBOX_ID = "citibox_id"
        const val EXTRA_DELIVERY_ID = "delivery_id"
    }
}
