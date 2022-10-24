package com.tsgreenberg.fm_schedule.ui.activities

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.AlarmClock
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.wear.compose.material.MaterialTheme
import com.tsgreenberg.core.navigation.NavConstants
import com.tsgreenberg.fm_schedule.ui.components.navigateToAlarm
import com.tsgreenberg.fm_schedule.ui.components.navigateToSchedule
import com.tsgreenberg.fm_schedule.ui.screens.SetAlarmScreen
import com.tsgreenberg.fm_schedule.ui.screens.UpcomingTrainsScreen
import com.tsgreenberg.fm_schedule.viewmodels.TrainScheduleViewModel
import com.tsgreenberg.ui_components.toFullStationName
import com.tsgreenberg.ui_components.toMinutes
import com.tsgreenberg.ui_components.toShortStationName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleActivity : ComponentActivity() {

    fun launchActivity(intent: Intent) {
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                NavHost(
                    navController = navController, startDestination = NavConstants.TRAIN_SCHEDULE
                ) {
                    navigateToSchedule(navController, intent)
                    navigateToAlarm(::launchActivity)
                }
            }
        }

    }


}