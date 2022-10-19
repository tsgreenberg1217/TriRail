package com.tsgreenberg.eta_info.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.tsgreenberg.eta_info.R
import com.tsgreenberg.eta_info.models.UiTrainSchedule
import com.tsgreenberg.eta_info.ui.components.TrackArrow
import com.tsgreenberg.ui_components.TriRailButton

@Composable
fun TrainScheduleList(trainSchedules: List<UiTrainSchedule>, onTimeSelect: (String) -> Unit) {
    if (trainSchedules.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {

            items(trainSchedules) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(3f), horizontalAlignment = Alignment.Start) {
                        TrackArrow(
                            14.sp, trackTxt = it.trainId.toString()
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(it.timeString, fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    TriRailButton(
                        modifier = Modifier.weight(2f),
                        onClick = {
                            onTimeSelect(it.timeString)
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
        }
    } else {
        Column(
            Modifier
                .fillMaxSize()
                .padding(12.dp), Arrangement.Center, Alignment.CenterHorizontally
        ) {
            Text(
                text = "No schedule information available for today", textAlign = TextAlign.Center
            )
        }
    }
}