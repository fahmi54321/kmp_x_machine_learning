package com.kmpxmachinelearning.shared.core.app

import com.kmpxmachinelearning.shared.core.error.UiError
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow

sealed class UiEvent {
    data class ShowLoading(val show: Boolean) : UiEvent()
    data class ShowError(val message: String) : UiEvent()
    data class ShowTimeout(val message: String, val onRetry: (() -> Unit)?) : UiEvent()
    data class ShowSuccess(val message: String) : UiEvent()
}

class AppState {

    private val _event = MutableSharedFlow<UiEvent>(
        extraBufferCapacity = 10
    )
    val event = _event.asSharedFlow()

    fun showLoading() {
        _event.tryEmit(UiEvent.ShowLoading(true))
    }

    fun hideLoading() {
        _event.tryEmit(UiEvent.ShowLoading(false))
    }

    fun showError(message: String) {
        _event.tryEmit(UiEvent.ShowError(message))
    }

    fun showTimeout(message: String, onRetry: (() -> Unit)?) {
        _event.tryEmit(UiEvent.ShowTimeout(message, onRetry))
    }

    fun showSuccess(message: String) {
        _event.tryEmit(UiEvent.ShowSuccess(message))
    }
}