package com.tsgreenberg.eta_info.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.tsgreenberg.eta_info.R
import com.tsgreenberg.eta_info.models.TrainScheduleState
import com.tsgreenberg.ui_components.TriRailButton
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

        if (state.trainSchedule.isNotEmpty()) {
            ScalingLazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(38.dp),
                state = scrollState
            ) {
                items(state.trainSchedule) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.Start) {
                            TrackArrow(
                                14.sp,
                                trackTxt = it.trainId.toString()
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = it.timeString,
                                fontSize = 22.sp
                            )
                        }

                        TriRailButton(
                            onClick = { /* Do something */ },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_alarm_add_black_24dp),
                                contentDescription = "set alarm",
                                modifier = Modifier
                                    .size(ButtonDefaults.SmallButtonSize)
                                    .wrapContentSize(align = Alignment.Center),
                                tint = Color.White
                            )
                        }


                    }
                }
            }
        }
    }
}