package com.tsgreenberg.fm_schedule.ui.components

import android.content.Intent
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tsgreenberg.core.navigation.NavConstants
import com.tsgreenberg.fm_schedule.ui.screens.UpcomingTrainsScreen
import com.tsgreenberg.fm_schedule.viewmodels.TrainScheduleViewModel
import com.tsgreenberg.ui_components.toShortStationName

fun NavGraphBuilder.navigateToSchedule(navController: NavController, intent: Intent) {
    composable(
        NavConstants.TRAIN_SCHEDULE,
        arguments = listOf(navArgument(NavConstants.STATION_ID) {
            type = NavType.IntType
            defaultValue = intent.extras?.getInt("stationId") ?: -1
        })

    ) {

        val stationShortName = (intent.extras?.getInt("stationId") ?: -1).toShortStationName()
        val viewModel: TrainScheduleViewModel = hiltViewModel()

        UpcomingTrainsScreen(
            state = viewModel.state.value
        ) {
            navController.navigate("${NavConstants.SET_TRAIN_ALARM}/$it/${stationShortName}")
        }
    }
}