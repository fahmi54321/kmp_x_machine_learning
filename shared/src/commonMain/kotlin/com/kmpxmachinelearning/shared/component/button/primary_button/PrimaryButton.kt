package com.kmpxmachinelearning.shared.component.button.primary_button

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
){
    val viewModel = koinViewModel<PrimaryButtonViewModel>()
    val state by viewModel.state.collectAsState()

    val scale by animateFloatAsState(
        targetValue = if (state.isPressed) 0.95f else 1f,
        animationSpec = tween(150)
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        viewModel.setPressed(true)
                        tryAwaitRelease()
                        viewModel.setPressed(false)
                        onClick()
                    }
                )
            }
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xff00c6ff),
                        Color(0xff0072ff)
                    )
                ),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 36.dp, vertical = 14.dp)
    ) {
        Text(
            text,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}