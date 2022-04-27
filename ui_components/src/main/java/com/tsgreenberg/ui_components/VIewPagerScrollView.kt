package com.tsgreenberg.ui_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ViewPagerScroll(
    pagerState: PagerState
){

    val scrollPosition = (pagerState.currentPage + pagerState.currentPageOffset)
        .coerceIn(0f, (pagerState.pageCount - 1).coerceAtLeast(0).toFloat())

    Canvas(
        Modifier
            .fillMaxSize()
            .rotate(-45f)
    ) {
        val stokeWidth = 5f
        val stroke = Stroke(
            width = stokeWidth,
            cap = StrokeCap.Round
        )
        val sizeArc = Size(size.width - stokeWidth, size.height - stokeWidth)
        val pos = Offset(
            (size.width - sizeArc.width) / 2f,
            (size.height - sizeArc.height) / 2f
        )

        val sweep = 90f

        drawArc(
            color = Color.White,
            topLeft = pos,
            style = stroke,
            useCenter = false,
            startAngle = 0f,
            sweepAngle = sweep,
            size = sizeArc,
            alpha = .4f
        )
        val maxRotation = sweep - (sweep / pagerState.pageCount)
        rotate(maxRotation * scrollPosition) {
            drawArc(
                color = Color.White,
                topLeft = pos,
                style = stroke,
                useCenter = false,
                startAngle = 0f,
                sweepAngle = sweep / pagerState.pageCount,
                size = sizeArc,
            )
        }

    }

}