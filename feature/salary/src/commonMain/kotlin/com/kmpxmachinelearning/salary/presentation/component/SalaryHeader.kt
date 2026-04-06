package com.kmpxmachinelearning.salary.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kmpxmachinelearning.shared.Alpha.NINETY_PERCENT
import com.kmpxmachinelearning.shared.Alpha.TWENTY_PERCENT
import com.kmpxmachinelearning.shared.FontFamily.bebasNeueFont
import com.kmpxmachinelearning.shared.FontFamily.robotoCondensedFont
import com.kmpxmachinelearning.shared.FontSize
import com.kmpxmachinelearning.shared.FontSize.SMALL
import com.kmpxmachinelearning.shared.Green
import com.kmpxmachinelearning.shared.White

@Composable
fun SalaryHeader(
    salary: String,
    category: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = salary,
            fontSize = FontSize.EXTRA_LARGE,
            fontWeight = FontWeight.Bold,
            color = Green,
            fontFamily = bebasNeueFont()
        )

        Spacer(Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .background(
                    Color(0xFF42A5F5).copy(alpha = TWENTY_PERCENT),
                    RoundedCornerShape(50)
                )
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = category,
                color = White.copy(alpha = NINETY_PERCENT),
                fontSize = SMALL,
                fontFamily = robotoCondensedFont()
            )
        }
    }
}