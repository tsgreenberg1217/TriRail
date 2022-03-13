package com.tsgreenberg.trirailwearos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.foundation.CurvedTextStyle
import androidx.wear.compose.material.CurvedText
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TimeTextDefaults

@Composable
@OptIn(ExperimentalWearMaterialApi::class, ExperimentalAnimationApi::class)
fun CustomTimeText(
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
            leadingCurvedContent = if (false) {
                {
                    CurvedText(
                        text = "",
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