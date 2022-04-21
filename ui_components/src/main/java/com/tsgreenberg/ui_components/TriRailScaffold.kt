package com.tsgreenberg.ui_components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.*

@Composable
fun TriRailScaffold(
    scalingLazyListState: ScalingLazyListState,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        positionIndicator = {
            if (scalingLazyListState.isScrollInProgress) {
                PositionIndicator(
                    scalingLazyListState =
                    scalingLazyListState
                )
            }
        },
        timeText = {
            CustomTimeText(
                visible = !scalingLazyListState.isScrollInProgress,
            )
        },
        vignette = {
            Vignette(vignettePosition = VignettePosition(0))
        }
    ) {
        content()

    }
}