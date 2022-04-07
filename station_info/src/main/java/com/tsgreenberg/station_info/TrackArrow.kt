package com.tsgreenberg.station_list

import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

@Composable
fun TrackArrow(
    modifier: Modifier,
    trackTxt: String) {
    Canvas(
        modifier
    ) {
        drawPath(
            path = Path().apply {
                moveTo(0f, 0f)
                lineTo(0f, size.height)
                lineTo(size.width * .8f, size.height)
                lineTo(size.width, size.height / 2)
                lineTo(size.width * .8f, 0f)
                close()
            },
            color = Color(0xFF4E90A6),
        )

        val fontSize = 40.toSp().toPx()
        val testDrawStart = (size.height / 2) + (fontSize/2)

        val textPaint = Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = fontSize
        }

        val txtRect = Rect().also {
            textPaint.getTextBounds(trackTxt,0 , trackTxt.length-1, it)
        }



        drawContext.canvas.nativeCanvas.apply {
            drawText(
                "#1099",
                12.dp.toPx(),
                testDrawStart,
                Paint().apply {
                    color = android.graphics.Color.WHITE
                    textSize = fontSize
                }
            )
        }
    }
}