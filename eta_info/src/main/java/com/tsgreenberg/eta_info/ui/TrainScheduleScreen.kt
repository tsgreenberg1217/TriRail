package com.tsgreenberg.eta_info.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.tsgreenberg.eta_info.models.TrainScheduleState
import com.tsgreenberg.ui_components.TriRailScaffold
import com.tsgreenberg.ui_components.toTimeString


@Composable
fun UpcomingTrainsScreen(state: TrainScheduleState) {
    val scalingLazyListState = rememberScalingLazyListState()
    TrainScheduleScreen(state, scalingLazyListState)
}

@Composable
internal fun TrainScheduleScreen(
    state: TrainScheduleState,
    scrollState: ScalingLazyListState
) {

    TriRailScaffold(
        progressBarState = state.progressBarState,
        scalingLazyListState = scrollState,
    ) {

        if (state.trainSchedule.isNotEmpty()){
            ScalingLazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                state = scrollState
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
                        Text(text = it.timeString)

                    }
                }
            }
        }
    }
}