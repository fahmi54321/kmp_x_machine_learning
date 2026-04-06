package com.kmpxmachinelearning.shared.core.service

// File: core/ui/overlay/AppOverlayController.kt

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kmpxmachinelearning.shared.util.HandleBackPress
import kotlinx.coroutines.delay

data class Loading(val count: Int) : OverlayState()

class AppOverlayController {
    var state by mutableStateOf<OverlayState>(OverlayState.None)

    private var loadingCount = 0

    fun showLoading() {
        println("showLoading()")
        loadingCount++
        state = OverlayState.Loading(loadingCount)
    }

    fun hideLoading() {
        println("hideLoading()")
        loadingCount--

        if (loadingCount <= 0) {
            loadingCount = 0

            // 🔥 JANGAN override kalau bukan loading
            if (state is OverlayState.Loading) {
                state = OverlayState.None
            }
        }
    }

    fun showError(message: String) {
        state = OverlayState.Error(message)
    }

    fun showSuccess(message: String) {
        state = OverlayState.Success(message)
    }

    fun showTimeout(message: String, onRetry: (() -> Unit)?) {
        state = OverlayState.Timeout(message, onRetry)
    }

    fun hide() {
        state = OverlayState.None
    }
}

sealed class OverlayState {
    object None : OverlayState()
    data class Loading(val id: Int) : OverlayState()
    data class Error(val message: String) : OverlayState()
    data class Success(val message: String) : OverlayState()
    data class Timeout(val message: String, val onRetry: (() -> Unit)?) : OverlayState()
}

// ================= UI =================

@Composable
fun GlobalOverlay(
    overlay: AppOverlayController,
                  cancelManager: CancelManager,

) {
    val state = overlay.state

    var isVisible by remember { mutableStateOf(false) }

    // 🔥 Sync state → visibility
    LaunchedEffect(state) {
        isVisible = state !is OverlayState.None
    }

    // BackHandler
    if (state !is OverlayState.None) {
        HandleBackPress {
            cancelManager.cancel()
            overlay.hide()
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is OverlayState.Loading -> GlassLoading()
                is OverlayState.Error -> GlassError(state.message) { overlay.hide() }
                is OverlayState.Success -> GlassSuccess(state.message) { overlay.hide() }
                is OverlayState.Timeout -> GlassTimeout(
                    state.message,
                    onRetry = {
                        overlay.hide()
                        state.onRetry?.invoke()
                    }
                )
                else -> Unit
            }
        }
    }
}

@Composable
fun GlassContainer(
    glowColor: Color = Color.Cyan,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(28.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1C1C1E), // atas
                        Color(0xFF121214)  // bawah (lebih gelap)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.06f), // subtle highlight
                shape = RoundedCornerShape(28.dp)
            )
            .padding(28.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

@Composable
fun GlassLoading() {
    GlassContainer {
        CircularProgressIndicator(color = Color.White)
        Spacer(Modifier.height(16.dp))
        Text("Loading...", color = Color.White)
    }
}

@Composable
fun GlassError(message: String, onDismiss: () -> Unit) {
    val infinite = rememberInfiniteTransition()
    val scale by infinite.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            tween(1500), RepeatMode.Reverse
        )
    )

    LaunchedEffect(Unit) {
        delay(10000)
        onDismiss()
    }

    GlassContainer {
        Box(modifier = Modifier.scale(scale)) {
            Text("❌", fontSize = MaterialTheme.typography.headlineLarge.fontSize)
        }

        Spacer(Modifier.height(16.dp))
        Text("Terjadi Kesalahan", color = Color.White)
        Spacer(Modifier.height(8.dp))
        Text(message, color = Color.White.copy(alpha = 0.7f))
    }
}

@Composable
fun GlassSuccess(message: String, onDismiss: () -> Unit) {
    GlassContainer {
        Text("✅", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(16.dp))
        Text(message, color = Color.White)

        Spacer(Modifier.height(16.dp))
        Button(onClick = onDismiss) {
            Text("OK")
        }
    }
}

@Composable
fun GlassTimeout(message: String, onRetry: () -> Unit) {
    GlassContainer {
        Text("⚠️", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(16.dp))
        Text("Koneksi!", color = Color.White)
        Spacer(Modifier.height(8.dp))
        Text(message, color = Color.White.copy(alpha = 0.7f))

        Spacer(Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

// ================= USAGE =================

/*
@Composable
fun AppRoot() {
    val overlay = remember { AppOverlayController() }

    Box {
        MainScreen()
        GlobalOverlay(overlay)
    }
}
*/
