package com.tsgreenberg.ui_components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.tsgreenberg.core.ProgressBarState

@Composable
fun TriRailScaffold(
    scalingLazyListState: ScalingLazyListState? = null,
    progressBarState: ProgressBarState = ProgressBarState.Idle,
    isVisible: Boolean = true,
    extraText: String = "",
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        positionIndicator = scalingLazyListState?.let {
            {
                if (scalingLazyListState.isScrollInProgress) {
                    PositionIndicator(scalingLazyListState = scalingLazyListState)
                }
            }
        },
        timeText = {
            CustomTimeText(
                extraText,
                visible = isVisible && (scalingLazyListState?.isScrollInProgress?.not() ?: true),
            )
        },
        vignette = {
            Vignette(vignettePosition = VignettePosition(0))
        }
    ) {

        if (progressBarState is ProgressBarState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val colors = remember {
                    listOf(TriRailColors.Blue, TriRailColors.Orange, TriRailColors.Green)
                }
                colors.forEachIndexed{ i, c ->
                    CircularProgressIndicator(
                        Modifier.size(100.dp - (i.dp * 12)),
                        indicatorColor = c,
                        trackColor = Color.Transparent,
                        strokeWidth = 1.dp + i.dp
                    )
                }

            }

        }else{
            content()
        }
    }
}