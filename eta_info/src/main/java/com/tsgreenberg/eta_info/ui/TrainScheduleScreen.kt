package com.tsgreenberg.eta_info.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.items
import androidx.wear.compose.material.rememberScalingLazyListState
import com.tsgreenberg.eta_info.models.TrainScheduleState
import com.tsgreenberg.ui_components.TriRailScaffold
import com.tsgreenberg.ui_components.toTimeString

@Composable
fun TrainScheduleScreen(
    state: TrainScheduleState
) {

    val scalingLazyListState = rememberScalingLazyListState()
    TriRailScaffold(
        progressBarState = state.progressBarState,
        scalingLazyListState = scalingLazyListState,
    ) {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            state = scalingLazyListState
        ) {
            items(state.trainSchedule) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TrackArrow(
                        modifier = Modifier.height(24.dp),
                        trackTxt = it.trainId.toString()
                    )
                    Text(text = it.timeInMins.toTimeString())

                }
            }
        }
    }
}