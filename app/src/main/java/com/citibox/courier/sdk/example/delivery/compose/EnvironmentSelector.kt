package com.citibox.courier.sdk.example.delivery.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.citibox.courier.sdk.webview.models.WebAppEnvironment

@Composable
fun EnvironmentSelector(actual: WebAppEnvironment, onItemClicked: (WebAppEnvironment) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Environment")
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = true
                }
                .padding(vertical = 8.dp, horizontal = 4.dp)
        ) {
            Row {
                Icon(
                    actual.icon(),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = actual.name)
            }
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "More"
            )
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            WebAppEnvironment.entries.forEach {
                DropdownMenuItem(
                    text = { Text(it.name) },
                    onClick = {
                        onItemClicked(it)
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            it.icon(),
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}

private fun WebAppEnvironment.icon(): ImageVector = when (this) {
    WebAppEnvironment.Production -> Icons.Outlined.CheckCircle
    WebAppEnvironment.Sandbox -> Icons.Outlined.Build
    WebAppEnvironment.Test -> Icons.Outlined.Warning
    WebAppEnvironment.Local -> Icons.Outlined.Home
}