package com.tsgreenberg.ui_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    val isRound = LocalContext.current.resources.configuration.isScreenRound
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

        content()
        if (progressBarState is ProgressBarState.Loading) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val colors = listOf(TriRailColors.Orange, TriRailColors.Blue, TriRailColors.Green)
                val color = remember {
                    colors.random()
                }
                CircularProgressIndicator(indicatorColor = color, trackColor = Color.Transparent)
            }

        }
    }
}