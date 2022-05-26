package com.tsgreenberg.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults

@Composable
fun TriRailButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color = TriRailColors.Blue,
    isEnabled: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    val buttonColor = if (isEnabled) color else Color.DarkGray
    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = { onClick() },
        modifier = modifier,
        enabled = isEnabled
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            buttonColor,
                            buttonColor.copy(alpha = 0.35f)
                        )
                    )
                )
                .matchParentSize(),
            contentAlignment = Alignment.Center,
        ) { content() }
    }
}