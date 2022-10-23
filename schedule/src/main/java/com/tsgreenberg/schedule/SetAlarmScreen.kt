package com.tsgreenberg.eta_info.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.tsgreenberg.ui_components.TriRailButton
import com.tsgreenberg.ui_components.TriRailColors
import com.tsgreenberg.ui_components.TriRailScaffold
import com.tsgreenberg.ui_components.toMinutes
import java.util.*

@Composable
fun SetAlarmScreen(stationName: String = "", etaInMins: Int, onAlarmSelect: (Int) -> Unit) {
    val nowInMins = Date().toMinutes()
    val currentDelta = etaInMins - nowInMins
    val minAllowedTime = 15
    TriRailScaffold(extraText = stationName) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "How many minutes before departure would you like to be reminded about your trip?",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp,
                    fontSize = 14.sp
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val buttonModifier = Modifier.height(40.dp)

                TriRailButton(
                    buttonModifier,
                    onClick = { onAlarmSelect(15) },
                ) {
                    Text(
                        text = "${minAllowedTime}min",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                    )
                }
                TriRailButton(
                    buttonModifier,
                    onClick = { onAlarmSelect(30) },
                    color = TriRailColors.Green,
                ) {
                    Text(
                        text = "30min",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                    )
                }

                TriRailButton(
                    buttonModifier,
                    onClick = { onAlarmSelect(60) },
                    color = TriRailColors.Orange,
                ) {
                    Text(
                        text = "1hr",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}
