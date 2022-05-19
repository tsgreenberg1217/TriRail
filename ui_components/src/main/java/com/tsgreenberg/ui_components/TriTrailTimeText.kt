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
@OptIn(ExperimentalWearMaterialApi::class, ExperimentalAnimationApi::class)
internal fun CustomTimeText(
    extraText: String = "",
    visible: Boolean,
//    showLeadingText: Boolean,
//    leadingText: String
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        TimeText(
            leadingCurvedContent = if (extraText.isNotEmpty()) {
                {
                    CurvedText(
                        text = extraText,
                        style = CurvedTextStyle(
                            color = Color.White
                        )
                    )
                }
            } else null,
            leadingLinearContent = if (false) {
                {
                    Text(
                        text = "",
                        style = TimeTextDefaults.timeTextStyle()
                    )
                }
            } else null,
        )
    }
}