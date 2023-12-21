package com.citibox.courier.sdk.example.delivery.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.citibox.courier.sdk.example.delivery.DeliveryViewModel
import com.citibox.courier.sdk.example.delivery.models.DeliverySideEffect
import com.citibox.courier.sdk.domain.DeliveryParams

@Composable
fun DeliveryScreen(
    viewModel: DeliveryViewModel,
    onLaunch: (params: DeliveryParams) -> Unit,
    onBack: () -> Unit,
) {

    val state = viewModel.state.collectAsState()

    DeliveryLayout(
        state = state.value,
        onAccessChanged = viewModel::onTokenChanged,
        onTrackingChanged = viewModel::onTrackingChanged,
        onPhoneChanged = viewModel::onPhoneChanged,
        onPhoneHashedChanged = viewModel::onPhoneHashedChanged,
        onDimensionsChanged = viewModel::onDimensionsChanged,
        onEnvironmentChanged = viewModel::onEnvironmentChanged,
        onBookingChanged = viewModel::onBookingIdChanged,
        onLaunchClicked = viewModel::onLaunch,
        onResultClearClicked = viewModel::onResultClear,
        onBack = onBack
    )

    LaunchedEffect(viewModel) {
        viewModel.sideEffect.collect {
            if (it is DeliverySideEffect.Launch) {
                onLaunch(it.params)
            }
        }
    }

}