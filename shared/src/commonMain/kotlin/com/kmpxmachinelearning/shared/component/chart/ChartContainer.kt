package com.kmpxmachinelearning.shared.component.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kmpxmachinelearning.shared.Alpha.FIVETEEN_PERCENT
import com.kmpxmachinelearning.shared.Alpha.TWENTY_PERCENT
import com.kmpxmachinelearning.shared.Black
import com.kmpxmachinelearning.shared.White

@Composable
fun ChartContainer(
    modifier: Modifier = Modifier,
    height: Dp? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height ?: 320.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Black.copy(alpha = FIVETEEN_PERCENT))
            .border(
                1.dp,
                White.copy(alpha = TWENTY_PERCENT),
                RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        content()
    }
}