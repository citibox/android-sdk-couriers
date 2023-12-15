package com.citibox.courier.sdk.example.retrieval.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.citibox.courier.sdk.domain.RetrievalParams
import com.citibox.courier.sdk.example.retrieval.RetrievalViewModel
import com.citibox.courier.sdk.example.retrieval.models.RetrievalSideEffect

@Composable
fun RetrievalScreen(
    viewModel: RetrievalViewModel,
    onLaunch: (params: RetrievalParams) -> Unit,
    onBack: () -> Unit
) {
    val state = viewModel.state.collectAsState()

    RetrievalLayout(
        state = state.value,
        onAccessChanged = viewModel::onAccessChanged,
        onCitiboxIdChanged = viewModel::onCitiboxIdChanged,
        onResultClearClicked = viewModel::onResultClear,
        onEnvironmentChanged = viewModel::onEnvironmentChanged,
        onLaunchClicked = viewModel::onLaunchClicked,
        onBack = onBack
    )

    LaunchedEffect(viewModel) {
        viewModel.sideEffect.collect {
            if (it is RetrievalSideEffect.Launch) {
                onLaunch(it.params)
            }
        }
    }
}