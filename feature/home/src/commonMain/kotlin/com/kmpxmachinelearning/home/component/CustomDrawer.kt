package com.kmpxmachinelearning.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kmpxmachinelearning.home.domain.DrawerItem
import com.kmpxmachinelearning.shared.FontFamily
import com.kmpxmachinelearning.shared.FontSize

@Composable
fun CustomDrawer(
    onSoon3Click: () -> Unit,
    onSoon4Click: () -> Unit,
    onSoon5Click: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.65f)
            .background(Color.White.copy(alpha = 0.05f))
            .border(1.dp, Color.White.copy(alpha = 0.1f))
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Machine Learning",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.White,
            fontFamily = FontFamily.bebasNeueFont(),
            fontSize = FontSize.EXTRA_LARGE
        )

        Text(
            text = "Model Implementation",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.White.copy(alpha = 0.7f),
            fontFamily = FontFamily.bebasNeueFont(),
            fontSize = FontSize.REGULAR
        )

        Spacer(modifier = Modifier.height(40.dp))

        DrawerItem.entries.take(5).forEach { item ->

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.07f))
                    .border(1.dp, Color.White.copy(alpha = 0.1f))
                    .clickable {
//                        when (item) {
//                            DrawerItem.Soon1 -> onSoon3Click()
//                            DrawerItem.Soon4 -> onSoon4Click()
//                            DrawerItem.Soon5 -> onSoon5Click()
//                            else -> {}
//                        }
                    }
                    .padding(16.dp)
            ) {
                Text(
                    text = item.title,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}