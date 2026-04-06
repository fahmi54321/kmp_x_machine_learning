package com.example.kmpxmachinelearning

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.kmpxmachinelearning.shared.navigation.Screen
import com.kmpxmachinelearning.navigation.SetupNavGraph
import com.kmpxmachinelearning.shared.core.service.AppOverlayController
import com.kmpxmachinelearning.shared.core.app.AppState
import com.kmpxmachinelearning.shared.core.service.CancelManager
import com.kmpxmachinelearning.shared.core.service.GlobalOverlay
import com.kmpxmachinelearning.shared.listeners.GlobalUiListener
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    val appState = koinInject<AppState>()
    val overlay = remember { AppOverlayController() }
    val cancelManager = koinInject<CancelManager>()
    val startDestination = remember {
        Screen.HomeGraph
    }
    MaterialTheme {
        GlobalUiListener(
            appState = appState,
            overlay = overlay
        ){
            Box {
                SetupNavGraph(
                    startDestination = startDestination
                )
                GlobalOverlay(
                    overlay = overlay,
                    cancelManager = cancelManager,
                )
            }
        }
    }
}