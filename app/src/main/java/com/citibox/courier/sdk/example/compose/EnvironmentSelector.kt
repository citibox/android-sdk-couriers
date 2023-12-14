package com.citibox.courier.sdk.example.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.citibox.courier.sdk.webview.models.WebAppEnvironment

@Composable
fun EnvironmentSelector(actual: WebAppEnvironment, onItemClicked: (WebAppEnvironment) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = true
                }
        ) {
            Text(text = "Environment: ${actual.name}")
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "More"
            )
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            WebAppEnvironment.entries.forEach {
                DropdownMenuItem(text = { Text(it.name) }, onClick = {
                    onItemClicked(it)
                    expanded = false
                })
            }
        }
    }
}