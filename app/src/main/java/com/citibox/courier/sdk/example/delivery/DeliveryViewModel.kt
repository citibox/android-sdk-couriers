package com.citibox.courier.sdk.example.delivery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.citibox.courier.sdk.example.delivery.models.DeliverySideEffect
import com.citibox.courier.sdk.example.delivery.models.DeliveryState
import com.citibox.courier.sdk.domain.DeliveryParams
import com.citibox.courier.sdk.domain.DeliveryResult
import com.citibox.courier.sdk.webview.models.WebAppEnvironment
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DeliveryViewModel : ViewModel() {

    private val _state = MutableStateFlow(DeliveryState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<DeliverySideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onTokenChanged(value: String) = _state.update { it.copy(token = value) }

    fun onTrackingChanged(value: String) = _state.update { it.copy(tracking = value) }

    fun onPhoneChanged(value: String) = _state.update { it.copy(phone = value) }

    fun onBookingIdChanged(value: String) = _state.update { it.copy(bookingId = value) }

    fun onPhoneHashedChanged(value: Boolean) = _state.update { it.copy(phoneHashed = value) }

    fun onDimensionsChanged(value: String) = _state.update { it.copy(dimensions = value) }

    fun onEnvironmentChanged(environment: WebAppEnvironment) =
        _state.update { it.copy(environment = environment) }

    fun onLaunch() {
        viewModelScope.launch {

            _state.update { it.copy(resultMessage = "") }

            val param = _state.value.toDeliveryParams()

            _sideEffect.trySend(DeliverySideEffect.Launch(param))
        }
    }

    private fun DeliveryState.toDeliveryParams() = DeliveryParams(
        accessToken = token,
        tracking = tracking,
        recipientPhone = phone,
        isPhoneHashed = phoneHashed,
        dimensions = dimensions,
        bookingId = bookingId,
        webAppEnvironment = environment
    )

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