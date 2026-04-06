package com.kmpxmachinelearning.shared.listeners

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.kmpxmachinelearning.shared.core.service.AppOverlayController
import com.kmpxmachinelearning.shared.core.app.AppState
import com.kmpxmachinelearning.shared.core.app.UiEvent

@Composable
fun GlobalUiListener(
    appState: AppState,
    overlay: AppOverlayController,
    content: @Composable () -> Unit
) {

    LaunchedEffect(Unit) {
        appState.event.collect { event ->

            when (event) {

                is UiEvent.ShowLoading -> {
                    if (event.show) overlay.showLoading()
                    else overlay.hideLoading()
                }

                is UiEvent.ShowError -> {
                    overlay.showError(event.message)
                }

                is UiEvent.ShowTimeout -> {
                    overlay.showTimeout(event.message, event.onRetry)
                }

                is UiEvent.ShowSuccess -> {
                    overlay.showSuccess(event.message)
                }
            }
        }
    }

    content()
}