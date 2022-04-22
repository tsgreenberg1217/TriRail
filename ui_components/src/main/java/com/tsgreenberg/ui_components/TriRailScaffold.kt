package com.tsgreenberg.ui_components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.*
import com.tsgreenberg.core.ProgressBarState

@Composable
fun TriRailScaffold(
    scalingLazyListState: ScalingLazyListState,
    progressBarState: ProgressBarState = ProgressBarState.Idle,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize().background(Color.Black),
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
        if (progressBarState is ProgressBarState.Loading) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator()
            }

        }
    }
}