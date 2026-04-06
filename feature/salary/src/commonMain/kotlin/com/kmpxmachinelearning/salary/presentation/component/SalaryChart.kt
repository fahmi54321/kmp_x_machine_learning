package com.kmpxmachinelearning.salary.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

data class HrPointEntity(
    val x: Float,
    val y: Float
)

@Composable
fun SalaryChart(
    curve: List<HrPointEntity>,
    real: List<HrPointEntity>,
    user: HrPointEntity?,
    onTouch: (HrPointEntity?) -> Unit,
    formatUSD: (Double) -> String,
) {
    var touchedPoint by remember { mutableStateOf<HrPointEntity?>(null) }

    Box {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->

                        val maxX = 11f
                        val maxY = (curve.maxOfOrNull { it.y } ?: 1f) * 1.2f

                        fun mapX(x: Float) = size.width * (x / maxX)
                        fun mapY(y: Float) = size.height - (size.height * (y / maxY))

                        val nearest = findNearestPoint(
                            offset,
                            curve + real + listOfNotNull(user),
                            ::mapX,
                            ::mapY
                        )

                        touchedPoint = nearest
                        onTouch(nearest)
                    }
                }
        ) {

            val maxX = 11f
            val maxY = (curve.maxOfOrNull { it.y } ?: 1f) * 1.2f

            fun mapX(x: Float) = size.width * (x / maxX)
            fun mapY(y: Float) = size.height - (size.height * (y / maxY))

            // DRAW LINE
            fun drawSmoothLine(points: List<HrPointEntity>, color: Color) {
                val path = Path()

                points.forEachIndexed { i, p ->
                    val x = mapX(p.x)
                    val y = mapY(p.y)

                    if (i == 0) {
                        path.moveTo(x, y)
                    } else {
                        val prev = points[i - 1]
                        val px = mapX(prev.x)
                        val py = mapY(prev.y)

                        val midX = (px + x) / 2

                        path.quadraticTo(px, py, midX, (py + y) / 2)
                        path.quadraticTo(x, y, x, y)
                    }
                }

                // LINE (utama)
                drawPath(
                    path = path,
                    color = color,
                    style = Stroke(width = 3f)
                )

                val gradientPath = Path().apply {
                    addPath(path)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }

                drawPath(
                    path = gradientPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            color.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    )
                )
            }

            drawSmoothLine(curve, Color(0xFF42A5F5))
            drawSmoothLine(real, Color.Red)

            user?.let {
                val center = Offset(mapX(it.x), mapY(it.y))

                // outer glow
                drawCircle(
                    color = Color(0xFF00E676).copy(alpha = 0.3f),
                    radius = 18f,
                    center = center
                )

                // white border
                drawCircle(
                    color = Color.White,
                    radius = 10f,
                    center = center
                )

                // inner
                drawCircle(
                    color = Color(0xFF00E676),
                    radius = 7f,
                    center = center
                )
            }

            // DOT
            fun drawDots(points: List<HrPointEntity>, color: Color) {
                points.forEach { p ->
                    val isTouched = touchedPoint?.x == p.x && touchedPoint?.y == p.y

                    val center = Offset(mapX(p.x), mapY(p.y))

                    // OUTLINE
                    drawCircle(
                        color = Color.White,
                        radius = if (isTouched) 10f else 6f,
                        center = center
                    )

                    // INNER
                    drawCircle(
                        color = color,
                        radius = if (isTouched) 7f else 4f,
                        center = center
                    )
                }
            }

            drawDots(curve, Color.Blue)
            drawDots(real, Color.Red)
        }

        // TOOLTIP
        touchedPoint?.let { point ->
            TooltipOverlay(
                point = point,
                formatUSD = formatUSD
            )
        }
    }
}

fun findNearestPoint(
    touch: Offset,
    points: List<HrPointEntity>,
    mapX: (Float) -> Float,
    mapY: (Float) -> Float
): HrPointEntity? {
    return points.minByOrNull {
        val px = mapX(it.x)
        val py = mapY(it.y)

        val dx = touch.x - px
        val dy = touch.y - py
        dx * dx + dy * dy
    }
}

@Composable
fun TooltipOverlay(
    point: HrPointEntity,
    formatUSD: (Double) -> String
) {
    Box(
        modifier = Modifier
            .offset(80.dp, 40.dp)
            .background(Color(0xFF1E1E1E), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column {
            Text("Level ${point.x}", color = Color.White)
            Text(formatUSD(point.y.toDouble()), color = Color.Green)
        }
    }
}