package com.tsgreenberg.eta_info.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.tsgreenberg.schedule.UiTrainSchedule
import com.tsgreenberg.ui_components.TrackArrow
import com.tsgreenberg.ui_components.TriRailButton
import com.tsgreenberg.schedule.R

@Composable fun ScheduleItem(schedule: UiTrainSchedule, onTimeSelect:(String) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            TrackArrow(
                14.sp, trackTxt = schedule.trainId.toString()
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(schedule.timeString, fontSize = 18.sp)
        }
        TriRailButton(
            onClick = {
                onTimeSelect(schedule.timeString)
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_alarm_add_black_24dp),
                contentDescription = "set alarm",
                modifier = Modifier
                    .size(32.dp)
                    .wrapContentSize(align = Alignment.Center),
                tint = Color.White
            )
        }
    }
}