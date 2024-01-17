package com.citibox.courier.sdk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.citibox.courier.sdk.domain.DeliveryParams
import com.citibox.courier.sdk.domain.DeliveryResult
import com.citibox.courier.sdk.domain.RetrievalParams
import com.citibox.courier.sdk.domain.RetrievalResult
import com.citibox.courier.sdk.theme.AndroidsdkcouriersTheme
import com.citibox.courier.sdk.webview.models.WebAppEnvironment

class TestingActivity : ComponentActivity() {

    private val deliveryLauncher = DeliveryLauncher(this, ::deliveryResult)
    private val retrievalLauncher = RetrievalLauncher(this, ::retrievalResult)

    var deliveryParams: DeliveryParams = defaultDeliveryParams()
    var retrievalParams: RetrievalParams = defaultRetrievalParams()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidsdkcouriersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TestingScreen(
                        onDelivery = { deliveryLauncher.launch(deliveryParams) },
                        onRetrieval = { retrievalLauncher.launch(retrievalParams) }
                    )
                }
            }
        }
    }

    private fun defaultDeliveryParams(): DeliveryParams = DeliveryParams(
        accessToken = "123",
        tracking = "456",
        recipientPhone = "789",
        webAppEnvironment = WebAppEnvironment.Production
    )

    private fun defaultRetrievalParams(): RetrievalParams = RetrievalParams(
        accessToken = "123",
        citiboxId = "456",
        webAppEnvironment = WebAppEnvironment.Production
    )

    private fun deliveryResult(result: DeliveryResult) {
        //
    }

    private fun retrievalResult(result: RetrievalResult) {
        //
    }
}
