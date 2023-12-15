package com.citibox.courier.sdk.example.delivery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.citibox.courier.sdk.DeliveryLauncher
import com.citibox.courier.sdk.domain.DeliveryResult
import com.citibox.courier.sdk.example.delivery.compose.DeliveryScreen
import com.citibox.courier.sdk.example.ui.theme.AndroidsdkcouriersTheme

class DeliveryActivity : ComponentActivity() {

    private val viewModel: DeliveryViewModel by viewModels()

    private val citiboxDeliveryLauncher = DeliveryLauncher(this, ::deliveryResult)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidsdkcouriersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DeliveryScreen(
                        viewModel = viewModel,
                        onLaunch = citiboxDeliveryLauncher::launch,
                        onBack = ::finish,
                    )
                }
            }
        }
    }

    private fun deliveryResult(result: DeliveryResult) {
        viewModel.deliveryResult(result = result)
    }
}