package com.citibox.courier.sdk.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.citibox.courier.sdk.DeliveryLauncher
import com.citibox.courier.sdk.example.compose.MainScreen
import com.citibox.courier.sdk.example.ui.theme.AndroidsdkcouriersTheme
import com.citibox.courier.sdk.domain.DeliveryResult

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val citiboxLauncher = DeliveryLauncher(this, ::deliveryResult)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidsdkcouriersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        viewModel = viewModel,
                        onLaunch = citiboxLauncher::launch,
                    )
                }
            }
        }
    }

    private fun deliveryResult(result: DeliveryResult) {
        viewModel.deliveryResult(result = result)
    }
}