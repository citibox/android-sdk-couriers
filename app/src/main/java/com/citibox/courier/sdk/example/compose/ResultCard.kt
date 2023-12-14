package com.citibox.courier.sdk.example.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultCard(resultMessage: String) {
    Card(
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            )
    ) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = 16.dp,
                    horizontal = 0.dp
                )
        ) {
            Text(
                text = "Result", style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )

            SelectionContainer {
                Text(
                    text = resultMessage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 8.dp,
                            start = 16.dp,
                            end = 16.dp,
                        )
                )
            }
        }
    }
}