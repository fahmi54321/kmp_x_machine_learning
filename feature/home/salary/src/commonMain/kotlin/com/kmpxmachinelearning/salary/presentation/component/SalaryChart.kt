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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
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
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    val maxX = 11f
    val maxY = (curve.maxOfOrNull { it.y } ?: 1f) * 1.2f

    // GLOBAL MAPPER
    val mapXGlobal: (Float) -> Float = { x ->
        canvasSize.width * (x / maxX)
    }

    val mapYGlobal: (Float) -> Float = { y ->
        canvasSize.height - (canvasSize.height * (y / maxY))
    }

    Box {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .onSizeChanged { canvasSize = it }
                .pointerInput(Unit) {
                    detectTapGestures { offset ->

                        val nearest = findNearestPoint(
                            touch = offset,
                            points = curve + real + listOfNotNull(user),
                            mapX = mapXGlobal,
                            mapY = mapYGlobal
                        )

                        touchedPoint = nearest
                        onTouch(nearest)
                    }
                }
        ) {

            val mapX: (Float) -> Float = { x -> size.width * (x / maxX) }
            val mapY: (Float) -> Float = { y -> size.height - (size.height * (y / maxY)) }

            // LINE
            drawSmoothLine(curve, Color(0xFF42A5F5), mapX, mapY)

            // USER POINT
            user?.let {
                drawUserPoint(it, mapX, mapY)
            }

            // DOTS
            drawDots(curve, Color.Blue, touchedPoint, mapX, mapY)
            drawDots(real, Color.Red, touchedPoint, mapX, mapY)
        }

        // TOOLTIP
        touchedPoint?.let {
            TooltipOverlay(
                point = it,
                formatUSD = formatUSD,
                mapX = mapXGlobal,
                mapY = mapYGlobal
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
    if (points.isEmpty()) return null

    return points.minByOrNull { point ->
        val dx = touch.x - mapX(point.x)
        val dy = touch.y - mapY(point.y)
        dx * dx + dy * dy
    }
}

@Composable
fun TooltipOverlay(
    point: HrPointEntity,
    formatUSD: (Double) -> String,
    mapX: (Float) -> Float,
    mapY: (Float) -> Float
) {
    val x = mapX(point.x)
    val y = mapY(point.y)

    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    x.toInt() - 120, // geser biar ke kiri
                    y.toInt() - 100  // geser ke atas titik
                )
            }
            .background(Color(0xFF1E1E1E), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column {
            Text(
                text = "Level ${point.x}",
                color = Color.White
            )
            Text(
                text = formatUSD(point.y.toDouble()),
                color = Color(0xFF00E676)
            )
        }
    }
}

fun DrawScope.drawSmoothLine(
    points: List<HrPointEntity>,
    color: Color,
    mapX: (Float) -> Float,
    mapY: (Float) -> Float
) {
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

    drawPath(path, color, style = Stroke(width = 3f))
}

fun DrawScope.drawDots(
    points: List<HrPointEntity>,
    color: Color,
    touchedPoint: HrPointEntity?,
    mapX: (Float) -> Float,
    mapY: (Float) -> Float
) {
    points.forEach { p ->
        val isTouched = touchedPoint?.x == p.x && touchedPoint?.y == p.y
        val center = Offset(mapX(p.x), mapY(p.y))

        drawCircle(Color.White, if (isTouched) 10f else 6f, center)
        drawCircle(color, if (isTouched) 7f else 4f, center)
    }
}

fun DrawScope.drawUserPoint(
    point: HrPointEntity,
    mapX: (Float) -> Float,
    mapY: (Float) -> Float
) {
    val center = Offset(mapX(point.x), mapY(point.y))

    drawCircle(Color(0xFF00E676).copy(alpha = 0.3f), 18f, center)
    drawCircle(Color.White, 10f, center)
    drawCircle(Color(0xFF00E676), 7f, center)
}

fun isStepData(points: List<HrPointEntity>): Boolean {
    if (points.size < 3) return false

    val flatCount = points.zipWithNext().count { it.first.y == it.second.y }
    val ratio = flatCount.toFloat() / (points.size - 1)

    return ratio > 0.6f
}