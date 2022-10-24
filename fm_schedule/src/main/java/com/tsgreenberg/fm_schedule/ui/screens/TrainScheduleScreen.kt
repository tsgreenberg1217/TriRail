package com.tsgreenberg.fm_schedule.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.tsgreenberg.fm_schedule.models.TrainScheduleState
import com.tsgreenberg.fm_schedule.ui.components.TrainScheduleList
import com.tsgreenberg.ui_components.TriRailColors
import com.tsgreenberg.ui_components.TriRailScaffold


@OptIn(ExperimentalPagerApi::class)
@Composable
fun UpcomingTrainsScreen(
    stationName: String = "", state: TrainScheduleState, onTimeSelect: (String) -> Unit
) {
    val scrollState = rememberScalingLazyListState()

    Box(Modifier.fillMaxSize()) {
        TriRailScaffold(
            extraText = stationName,
            progressBarState = state.progressBarState,
            error = state.error,
            scalingLazyListState = scrollState,
        ) {
            val pagerState = rememberPagerState()


            Column {
                Spacer(modifier = Modifier.size(20.dp))
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    backgroundColor = Color.Transparent,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                            color = TriRailColors.Orange
                        )
                    },

                    ) {
                    listOf("North", "South").forEachIndexed { i, s ->
                        Tab(
                            text = { Text(s) },
                            selected = pagerState.currentPage == i,
                            onClick = { /* TODO */ },
                            selectedContentColor = TriRailColors.Orange,
                            unselectedContentColor = Color.White
                        )
                    }
                }
                val schedules = state.trainSchedule
                HorizontalPager(
                    state = pagerState, count = state.trainSchedule.size
                ) { page ->
                    schedules[if (page == 0) "North" else "South"]?.let {
                        TrainScheduleList(
                            trainSchedules = it, onTimeSelect = onTimeSelect
                        )
                    }
                }
            }

        }
    }
}