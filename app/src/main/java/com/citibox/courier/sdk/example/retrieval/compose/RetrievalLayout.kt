package com.citibox.courier.sdk.example.retrieval.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.citibox.courier.sdk.example.delivery.compose.EnvironmentSelector
import com.citibox.courier.sdk.example.delivery.compose.ResultCard
import com.citibox.courier.sdk.example.retrieval.models.RetrievalState
import com.citibox.courier.sdk.example.ui.theme.components.InputDataField
import com.citibox.courier.sdk.webview.models.WebAppEnvironment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetrievalLayout(
    state: RetrievalState,
    onAccessChanged: (String) -> Unit,
    onCitiboxIdChanged: (String) -> Unit,
    onResultClearClicked: () -> Unit,
    onEnvironmentChanged: (WebAppEnvironment) -> Unit,
    onLaunchClicked: () -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "📤 Retrieval Launcher Demo") },
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
                textDefault = state.citiboxId,
                label = "🪪 Citibox ID *",
                isMandatory = true,
                onValueChanged = onCitiboxIdChanged,
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
                    text = "Launch Citibox Retrieval",
                )
            }
        }
    }
}