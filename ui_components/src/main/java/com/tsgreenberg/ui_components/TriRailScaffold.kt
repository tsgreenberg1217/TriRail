package com.tsgreenberg.ui_components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.tsgreenberg.core.ProgressBarState

@Composable
fun TriRailScaffold(
    scalingLazyListState: ScalingLazyListState? = null,
    progressBarState: ProgressBarState = ProgressBarState.Idle,
    error: String? = null,
    isVisible: Boolean = true,
    extraText: String = "",
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        positionIndicator = scalingLazyListState?.let {
            {
                if (it.isScrollInProgress) PositionIndicator(scalingLazyListState = scalingLazyListState)
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

        when {
            progressBarState is ProgressBarState.Loading -> TriRailLoadingSpinner()
            error != null -> TriRailErrorScreen(error)
        }
        if (error == null) content()

    }
}

@Composable
internal fun TriRailLoadingSpinner() {
    Box(
        modifier = Modifier.fillMaxSize().testTag("loading_spinner_test"),
        contentAlignment = Alignment.Center
    ) {
        val colors = remember {
            listOf(TriRailColors.Blue, TriRailColors.Orange, TriRailColors.Green)
        }
        colors.forEachIndexed { i, c ->
            CircularProgressIndicator(
                Modifier.size(100.dp - (i.dp * 12)),
                indicatorColor = c,
                trackColor = Color.Transparent,
                strokeWidth = 1.dp + i.dp
            )
        }

    }
}


@Composable
internal fun TriRailErrorScreen(errorMsg: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error", style = TextStyle(
                fontSize = 18.sp,
                color = TriRailColors.Orange,
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = errorMsg, style = TextStyle(
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        )

    }
}