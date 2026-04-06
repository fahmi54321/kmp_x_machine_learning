package com.kmpxmachinelearning.shared.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kmpxmachinelearning.shared.Alpha.EIGHT_PERCENT
import com.kmpxmachinelearning.shared.Alpha.TWENTY_PERCENT
import com.kmpxmachinelearning.shared.White

@Composable
fun PrimaryCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .widthIn(max = 600.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(White.copy(alpha = EIGHT_PERCENT))
            .border(
                1.dp,
                White.copy(alpha = TWENTY_PERCENT),
                RoundedCornerShape(30.dp)
            )
            .padding(32.dp)
    ) {
        content()
    }
}