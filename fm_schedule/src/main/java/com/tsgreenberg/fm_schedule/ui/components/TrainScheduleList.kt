package com.tsgreenberg.fm_schedule.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.tsgreenberg.fm_schedule.models.TrainSchedule

@Composable
fun TrainScheduleList(trainSchedules: List<TrainSchedule>, onTimeSelect: (String) -> Unit) {
    val isRound = LocalContext.current.resources.configuration.isScreenRound
    if (trainSchedules.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item { Spacer(modifier = Modifier) }
            items(trainSchedules) { schedule ->
                ScheduleItem(
                    schedule = schedule,
                    onTimeSelect = onTimeSelect
                )
            }
            if (isRound) item { Spacer(modifier = Modifier.size(40.dp)) }

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