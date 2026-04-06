package com.kmpxmachinelearning.home.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kmpxmachinelearning.home.domain.BottomBarDestination
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    selected: BottomBarDestination,
    onSelect: (BottomBarDestination) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)) // penting
            .background(Color.White.copy(alpha = 0.08f))
            .border(
                1.dp,
                Color.White.copy(alpha = 0.15f),
                RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 20.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        BottomBarDestination.entries.forEach { destination ->

            val tint by animateColorAsState(
                targetValue = if (selected == destination)
                    Color.White
                else
                    Color.White.copy(alpha = 0.5f)
            )

            Icon(
                painter = painterResource(destination.icon),
                contentDescription = null,
                tint = tint,
                modifier = Modifier.clickable { onSelect(destination) }
            )
        }
    }
}