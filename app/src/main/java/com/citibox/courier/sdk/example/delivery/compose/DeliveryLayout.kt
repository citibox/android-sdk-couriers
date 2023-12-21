package com.citibox.courier.sdk.example.delivery.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.citibox.courier.sdk.example.delivery.models.DeliveryState
import com.citibox.courier.sdk.example.ui.theme.components.InputDataField
import com.citibox.courier.sdk.webview.models.WebAppEnvironment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryLayout(
    state: DeliveryState,
    onAccessChanged: (String) -> Unit,
    onTrackingChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onPhoneHashedChanged: (Boolean) -> Unit,
    onDimensionsChanged: (String) -> Unit,
    onBookingChanged: (String) -> Unit,
    onEnvironmentChanged: (WebAppEnvironment) -> Unit,
    onLaunchClicked: () -> Unit,
    onResultClearClicked: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "📥 Delivery Launcher Demo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(bottom = 16.dp),
        ) {

            AnimatedVisibility(visible = state.resultMessage.isNotEmpty()) {
                ResultCard(
                    resultMessage = state.resultMessage,
                    onClear = onResultClearClicked
                )
            }

            InputDataField(
                textDefault = state.token,
                label = "🔑 Access token *",
                isMandatory = true,
                onValueChanged = onAccessChanged,
            )
            InputDataField(
                textDefault = state.tracking,
                label = "🪪 Tracking *",
                isMandatory = true,
                onValueChanged = onTrackingChanged,
            )
            InputDataField(
                textDefault = state.phone,
                label = "☎️ Phone *",
                isMandatory = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                onValueChanged = onPhoneChanged,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onPhoneHashedChanged(!state.phoneHashed)
                    },
            ) {
                Checkbox(
                    checked = state.phoneHashed,
                    onCheckedChange = onPhoneHashedChanged,
                )
                Text(
                    text = "Use the phone hashed (SHA256)",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(alignment = Alignment.CenterVertically),
                )
            }
            InputDataField(
                textDefault = state.bookingId,
                label = "🎟️ Booking Id - optional",
                onValueChanged = onBookingChanged,
            )
            InputDataField(
                textDefault = state.dimensions,
                label = "📐 Dimensions ([mm]x[mm]x[mm]) - optional",
                onValueChanged = onDimensionsChanged,
            )
            EnvironmentSelector(
                actual = state.environment,
                onItemClicked = onEnvironmentChanged
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 32.dp,
                        horizontal = 16.dp,
                    ),
                onClick = onLaunchClicked,
            ) {
                Text(
                    text = "Launch Citibox Delivery",
                )
            }
        }
    }
}