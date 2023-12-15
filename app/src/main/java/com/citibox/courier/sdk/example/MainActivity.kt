package com.citibox.courier.sdk.example

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.citibox.courier.sdk.example.delivery.DeliveryActivity
import com.citibox.courier.sdk.example.retrieval.RetrievalActivity
import com.citibox.courier.sdk.example.ui.theme.AndroidsdkcouriersTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidsdkcouriersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = "Citibox SDK Couriers example",
                            style = MaterialTheme.typography.headlineLarge
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = ::launchDelivery,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "📥 Delivery example")
                        }

                        Button(
                            onClick = ::launchRetrieval,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "📤 Retrieval example")
                        }
                    }
                }
            }
        }
    }

    private fun launchDelivery() {
        startActivity(Intent(this, DeliveryActivity::class.java))
    }

    private fun launchRetrieval() {
        startActivity(Intent(this, RetrievalActivity::class.java))
    }
}