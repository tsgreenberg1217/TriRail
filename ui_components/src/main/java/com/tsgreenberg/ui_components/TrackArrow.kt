package com.tsgreenberg.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text

@Composable
fun TrackArrow(
    fontSize: TextUnit,
    trackTxt: String,
    colorValue: Color = Color(0xFF4E90A6)

) {


    Row(
        modifier = Modifier.height(fontSize.value.dp + 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            Modifier.fillMaxHeight().background(colorValue),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                modifier = Modifier.padding(start = 3.dp),
                text = trackTxt,
                fontSize = fontSize,
            )
        }

        Box(
            Modifier
                .width(10.dp)
                .fillMaxHeight()
                .drawBehind {
                    drawPath(
                        path = Path().apply {
                            moveTo(0f, 0f)
                            lineTo(0f, size.height)
                            lineTo(size.width, size.height / 2)
                            close()
                        },
                        color = colorValue,
                    )
                }
        )
    }
}