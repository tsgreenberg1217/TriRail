package com.tsgreenberg.ui_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.foundation.CurvedTextStyle
import androidx.wear.compose.material.*

@Composable
internal fun CustomTimeText(
    extraText: String = "",
    visible: Boolean,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        TimeText(
            startCurvedContent = if (extraText.isNotEmpty()) {
                {
                    curvedText(
                        text = extraText,
                        style = CurvedTextStyle(
                            color = Color.White
                        )
                    )
                }
            } else null,
            startLinearContent = if (extraText.isNotEmpty()) {
                { Text(text = extraText) }
            } else null
        )
    }
}