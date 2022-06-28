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
) {

    val sweep = 45f

    val isRound = LocalContext.current.resources.configuration.isScreenRound
    val scrollPosition = (pagerState.currentPage + pagerState.currentPageOffset)
        .coerceIn(0f, (pagerState.pageCount - 1).coerceAtLeast(0).toFloat())

    Canvas(
        Modifier
            .fillMaxSize()
            .rotate(if (isRound) -(sweep/2) else 0f)
    ) {
        val stokeWidth = 5f
        val stroke = Stroke(
            width = stokeWidth,
            cap = StrokeCap.Round
        )
        val strokeOffset = Size(size.width - stokeWidth, size.height - stokeWidth)
        if (isRound) {
            val pos = Offset(
                (size.width - strokeOffset.width) / 2f,
                (size.height - strokeOffset.height) / 2f
            )

            drawArc(
                color = Color.White,
                topLeft = pos,
                style = stroke,
                useCenter = false,
                startAngle = 0f,
                sweepAngle = sweep,
                size = strokeOffset,
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
                    size = strokeOffset,
                )
            }
        } else {
            val startOffset =
                size.height.let { h -> h - (h / pagerState.pageCount) } * scrollPosition
            val endOffset = startOffset + size.height / pagerState.pageCount
            drawLine(
                strokeWidth = stokeWidth,
                color = Color.White,
                start = Offset(
                    strokeOffset.width,
                    0f
                ),
                end = Offset(
                    strokeOffset.width,
                    size.height,
                ),
                cap = StrokeCap.Round,
                alpha = .4f
            )
            drawLine(
                strokeWidth = stokeWidth,
                color = Color.White,
                start = Offset(
                    strokeOffset.width,
                    startOffset
                ),
                end = Offset(
                    strokeOffset.width,
                    endOffset
                ),
                cap = StrokeCap.Round
            )


        }

    }

}