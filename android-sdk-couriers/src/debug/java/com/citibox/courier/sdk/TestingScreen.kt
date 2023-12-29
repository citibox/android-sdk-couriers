package com.citibox.courier.sdk

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

internal const val TestTagDelivery = "TestTagDelivery"
internal const val TestTagRetrieval = "TestTagRetrieval"

@Composable
fun TestingScreen(
    onDelivery: () -> Unit,
    onRetrieval: () -> Unit
) {
    Column {
        TextButton(
            onClick = onDelivery,
            modifier = Modifier
                .testTag(TestTagDelivery)
        ) {
            Text(text = "Delivery test")
        }

        TextButton(
            onClick = onRetrieval,
            modifier = Modifier
                .testTag(TestTagRetrieval)
        ) {
            Text(text = "Retrieval test")
        }
    }
}