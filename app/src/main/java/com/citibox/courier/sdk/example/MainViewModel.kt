package com.citibox.courier.sdk.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.citibox.courier.sdk.example.models.MainSideEffect
import com.citibox.courier.sdk.example.models.MainState
import com.citibox.courier.sdk.domain.DeliveryParams
import com.citibox.courier.sdk.domain.DeliveryResult
import com.citibox.courier.sdk.webview.models.WebAppEnvironment
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<MainSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onTokenChanged(value: String) = _state.update { it.copy(token = value) }

    fun onTrackingChanged(value: String) = _state.update { it.copy(tracking = value) }

    fun onPhoneChanged(value: String) = _state.update { it.copy(phone = value) }

    fun onPhoneHashedChanged(value: Boolean) = _state.update { it.copy(phoneHashed = value) }

    fun onDimensionsChanged(value: String) = _state.update { it.copy(dimensions = value) }

    fun onEnvironmentChanged(environment: WebAppEnvironment) =
        _state.update { it.copy(environment = environment) }

    fun onLaunch() {
        viewModelScope.launch {

            _state.update { it.copy(resultMessage = "") }

            val param = DeliveryParams(
                accessToken = _state.value.token,
                tracking = _state.value.tracking,
                recipientPhone = _state.value.phone,
                isPhoneHashed = _state.value.phoneHashed,
                dimensions = _state.value.dimensions,
                webAppEnvironment = _state.value.environment
            )

            _sideEffect.trySend(MainSideEffect.Launch(param))
        }
    }

    fun onResultClear() {
        _state.update {
            it.copy(resultMessage = "")
        }
    }

    fun deliveryResult(result: DeliveryResult) {
        viewModelScope.launch {
            val msg = when (result) {
                is DeliveryResult.Cancel -> result.toMsg()
                is DeliveryResult.Error -> result.toMsg()
                is DeliveryResult.Failure -> result.toMsg()
                is DeliveryResult.Success -> result.toMsg()
            }

            _state.update { it.copy(resultMessage = msg) }
        }
    }

    private fun DeliveryResult.Cancel.toMsg(): String =
        "Unsuccessful\n\n" +
                "Cancel\n" +
                "Message: $type"

    private fun DeliveryResult.Error.toMsg(): String =
        "Unsuccessful\n\n" +
                "Error\n" +
                "Message: $errorCode"

    private fun DeliveryResult.Failure.toMsg(): String =
        "Unsuccessful\n\n" +
                "Failure\n" +
                "Message: $type"

    private fun DeliveryResult.Success.toMsg(): String =
        "Success\n\n" +
                "Box number: $boxNumber\n" +
                "Citibox Id: $citiboxId\n" +
                "Delivery Id: $deliveryId"
}