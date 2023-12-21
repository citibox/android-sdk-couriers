package com.citibox.courier.sdk.example.retrieval

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.citibox.courier.sdk.domain.RetrievalParams
import com.citibox.courier.sdk.domain.RetrievalResult
import com.citibox.courier.sdk.example.retrieval.models.RetrievalSideEffect
import com.citibox.courier.sdk.example.retrieval.models.RetrievalState
import com.citibox.courier.sdk.webview.models.WebAppEnvironment
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RetrievalViewModel : ViewModel() {
    private val _state = MutableStateFlow(RetrievalState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<RetrievalSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onAccessChanged(value: String) = _state.update { it.copy(token = value) }
    fun onCitiboxIdChanged(value: String) = _state.update { it.copy(citiboxId = value) }
    fun onResultClear() = _state.update { it.copy(resultMessage = "") }
    fun onEnvironmentChanged(value: WebAppEnvironment) =
        _state.update { it.copy(environment = value) }

    fun onLaunchClicked() {
        viewModelScope.launch {

            _state.update { it.copy(resultMessage = "") }

            val param = RetrievalParams(
                accessToken = _state.value.token,
                citiboxId = _state.value.citiboxId,
                webAppEnvironment = _state.value.environment
            )

            _sideEffect.trySend(RetrievalSideEffect.Launch(param))
        }
    }

    fun retrievalResult(result: RetrievalResult) {
        viewModelScope.launch {
            val msg = when (result) {
                is RetrievalResult.Cancel -> result.toMsg()
                is RetrievalResult.Error -> result.toMsg()
                is RetrievalResult.Failure -> result.toMsg()
                is RetrievalResult.Success -> result.toMsg()
            }

            _state.update { it.copy(resultMessage = msg) }
        }
    }

    private fun RetrievalResult.Cancel.toMsg(): String =
        "Unsuccessful\n\n" +
                "Cancel\n" +
                "Message: $type"

    private fun RetrievalResult.Error.toMsg(): String =
        "Unsuccessful\n\n" +
                "Error\n" +
                "Message: $errorCode"

    private fun RetrievalResult.Failure.toMsg(): String =
        "Unsuccessful\n\n" +
                "Failure\n" +
                "Message: $type"

    private fun RetrievalResult.Success.toMsg(): String =
        "Success\n\n" +
                "Box number: $boxNumber\n" +
                "Citibox Id: $citiboxId\n"

}