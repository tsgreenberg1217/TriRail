package com.tsgreenberg.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text

@Composable
fun TriRailButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = { onClick() },
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF4E90A6),
                            Color(0xFF132329)
                        )
                    )
                )
                .matchParentSize(),
            contentAlignment = Alignment.Center,
        ) { content() }
    }
}