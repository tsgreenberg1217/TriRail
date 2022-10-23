package com.tsgreenberg.ui_components//package com.tsgreenberg.eta_info.ui
//
//import android.content.res.Resources
//import android.graphics.Paint
//import android.graphics.Rect
//import android.util.TypedValue
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.IntrinsicSize
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentWidth
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.nativeCanvas
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.TextUnit
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun TrackArrow(
//    fontSize:TextUnit,
//    modifier: Modifier = Modifier,
//    trackTxt: String
//) {
//    val fontSizePx = TypedValue.applyDimension(
//        TypedValue.COMPLEX_UNIT_SP,
//        fontSize.value,
//        LocalContext.current.resources.displayMetrics
//    )
//    val textPaint = Paint().apply {
//        color = android.graphics.Color.WHITE
//        textSize = fontSizePx
//        isAntiAlias = true
//    }
//
//    val textWidth = textPaint.measureText(trackTxt)
//
//    val canvasWidth = TypedValue.applyDimension(
//        TypedValue.COMPLEX_UNIT_DIP,
//        textWidth,
//        LocalContext.current.resources.displayMetrics
//    )
//
//    Canvas(
//        modifier.then(
//            Modifier.width(IntrinsicSize.valueOf()).background(Color.Magenta)
//        )
//    ) {
////        val textLeftPadding = 4.dp.toPx()
//
//        val txtRect = Rect().also {
//            textPaint.getTextBounds(trackTxt, 0, trackTxt.length, it)
//        }
//
//        val colorValue = 0xFF4E90A6
//
//        drawPath(
//            path = Path().apply {
//                moveTo(0f, 0f)
//                lineTo(0f, size.height)
//                lineTo(textWidth, size.height)
////                lineTo(size.width, size.height / 2)
//                lineTo(textWidth, 0f)
//                close()
//            },
//
//            color = Color(colorValue),
//        )
//
////        val textY = size.height - ((size.height - txtRect.height()) / 2)
//
//        drawContext.canvas.nativeCanvas.apply {
//            drawText(
//                trackTxt,
//                0f,
//                size.height,
//                Paint().apply {
//                    color = android.graphics.Color.WHITE
//                    textSize = fontSizePx
//                }
//            )
//        }
//    }
//}