package com.tsgreenberg.fm_schedule.ui.components

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tsgreenberg.core.navigation.NavConstants
import com.tsgreenberg.fm_schedule.ui.screens.UpcomingTrainsScreen
import com.tsgreenberg.fm_schedule.viewmodels.TrainScheduleViewModel
import com.tsgreenberg.ui_components.toShortStationName

fun NavGraphBuilder.navigateToSchedule(stationId: Int, goToSetAlarm: (String, Int) -> Unit) {
    composable(
        NavConstants.TRAIN_SCHEDULE, arguments = listOf(navArgument(NavConstants.STATION_ID) {
            type = NavType.IntType
            defaultValue = stationId
        })

    ) {

        val viewModel: TrainScheduleViewModel = hiltViewModel()

        UpcomingTrainsScreen(
            stationName = stationId.toShortStationName(),
            state = viewModel.state.value
        ) {
            goToSetAlarm(it, stationId)
        }
    }
}