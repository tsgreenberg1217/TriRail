package com.tsgreenberg.schedule.ui.components

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
import com.tsgreenberg.schedule.R
import com.tsgreenberg.schedule.models.TrainSchedule
import com.tsgreenberg.ui_components.TrackArrow
import com.tsgreenberg.ui_components.TriRailButton

@Composable fun ScheduleItem(schedule: TrainSchedule, onTimeSelect:(String) -> Unit) {
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