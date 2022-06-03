package com.tsgreenberg.eta_info.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val delta = etaInMins - nowInMins
    TriRailScaffold(extraText = stationName) {
        val buttonModifier = Modifier
            .width(65.dp)
            .padding(3.dp)

        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp)
            )
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = "When would you like to be reminded about your trip?",
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
            )
            TriRailButton(
                buttonModifier,
                onClick = { onAlarmSelect(60) },
                color = TriRailColors.Orange,
                isEnabled = delta >= 60
            ) {
                Text(
                    text = "1hr",
                    textAlign = TextAlign.Center,
                )
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TriRailButton(
                    buttonModifier,
                    onClick = { onAlarmSelect(15) },
                    isEnabled = delta >= 15
                ) {
                    Text(
                        text = "15min",
                        textAlign = TextAlign.Center,
                    )
                }
                TriRailButton(
                    buttonModifier,
                    onClick = { onAlarmSelect(30) },
                    color = TriRailColors.Green,
                    isEnabled = delta >= 30
                ) {
                    Text(
                        text = "30min",
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }

    }

}