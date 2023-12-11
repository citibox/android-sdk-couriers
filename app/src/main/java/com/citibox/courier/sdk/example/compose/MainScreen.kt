package com.citibox.courier.sdk.example.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.citibox.courier.sdk.example.MainViewModel
import com.citibox.courier.sdk.example.models.MainSideEffect
import com.citibox.courier.sdk.domain.DeliveryParams

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onLaunch: (params: DeliveryParams) -> Unit
) {

    val state = viewModel.state.collectAsState()

    MainLayout(
        state = state.value,
        onAccessChanged = viewModel::onTokenChanged,
        onTrackingChanged = viewModel::onTrackingChanged,
        onPhoneChanged = viewModel::onPhoneChanged,
        onPhoneHashedChanged = viewModel::onPhoneHashedChanged,
        onDimensionsChanged = viewModel::onDimensionsChanged,
        onLaunchClicked = viewModel::onLaunch
    )

    LaunchedEffect(viewModel) {
        viewModel.sideEffect.collect {
            if (it is MainSideEffect.Launch) {
                onLaunch(it.params)
            }
        }
    }

}