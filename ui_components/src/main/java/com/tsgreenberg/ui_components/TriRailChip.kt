package com.tsgreenberg.ui_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Text

@Composable
fun TriRailChip(modifier: Modifier = Modifier, text: String, color: Color, onClick: () -> Unit) {
    Chip(modifier = modifier, onClick = { onClick() },
        colors = ChipDefaults.gradientBackgroundChipColors(
            startBackgroundColor = color,
            endBackgroundColor = Color(0xFF132329)
        ),
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                textAlign = TextAlign.Center
            )
        }
    )
}