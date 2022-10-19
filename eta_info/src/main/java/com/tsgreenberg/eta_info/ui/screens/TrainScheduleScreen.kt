package com.tsgreenberg.eta_info.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.tsgreenberg.eta_info.models.TrainScheduleState
import com.tsgreenberg.eta_info.utils.TestingTags
import com.tsgreenberg.ui_components.TriRailScaffold


@OptIn(ExperimentalPagerApi::class)
@Composable
fun UpcomingTrainsScreen(
    stationName: String = "", state: TrainScheduleState, onTimeSelect: (String) -> Unit
) {
    val scrollState = rememberScalingLazyListState()

    Box(
        Modifier
            .fillMaxSize()
            .testTag(TestingTags.SCHEDULE_LIST)
    ) {
        TriRailScaffold(
            extraText = stationName,
            progressBarState = state.progressBarState,
            error = state.error,
            scalingLazyListState = scrollState,
        ) {
            val pagerState = rememberPagerState()


            TabRow(selectedTabIndex = pagerState.currentPage, indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }) {
                listOf("North", "South").forEachIndexed { i, s ->
                    Tab(
                        text = { Text(s) },
                        selected = pagerState.currentPage == i,
                        onClick = { /* TODO */ },
                    )
                }
            }
            val schedules = state.trainSchedule
            HorizontalPager(state.trainSchedule.size) { page ->
                schedules[if (page == 0) "North" else "South"]?.let {
                    TrainScheduleList(
                        trainSchedules = it,
                        onTimeSelect = onTimeSelect
                    )
                }
            }

        }
    }
}