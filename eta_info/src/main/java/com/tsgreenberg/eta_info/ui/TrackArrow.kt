package com.tsgreenberg.eta_info.ui

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
    modifier: Modifier = Modifier,
    trackTxt: String
) {
    Canvas(
        modifier
    ) {

        val fontSize = 24.toSp().toPx()
        val textLeftPadding = 4.dp.toPx()

        val textPaint = Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = fontSize
            isAntiAlias = true
        }

        val txtRect = Rect().also {
            textPaint.getTextBounds(trackTxt, 0, trackTxt.length, it)
        }

        val arrowWidth = textLeftPadding + txtRect.width()
        val colorValue = 0xFF4E90A6
        drawPath(
            path = Path().apply {
                moveTo(0f, 0f)
                lineTo(0f, size.height)
                lineTo(arrowWidth, size.height)
                lineTo(arrowWidth + 15.dp.toPx(), size.height / 2)
                lineTo(arrowWidth, 0f)
                close()
            },

            color = Color(colorValue),
        )

        val textY = size.height - ((size.height - txtRect.height()) / 2)

        drawContext.canvas.nativeCanvas.apply {
            drawText(
                trackTxt,
                textLeftPadding,
                textY,
                Paint().apply {
                    color = android.graphics.Color.WHITE
                    textSize = fontSize
                }
            )
        }
    }
}