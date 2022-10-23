package com.tsgreenberg.schedule.ui.activities

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
import com.tsgreenberg.schedule.viewmodels.TrainScheduleViewModel
import com.tsgreenberg.schedule.ui.screens.SetAlarmScreen
import com.tsgreenberg.schedule.ui.screens.UpcomingTrainsScreen
import com.tsgreenberg.ui_components.toFullStationName
import com.tsgreenberg.ui_components.toMinutes
import com.tsgreenberg.ui_components.toShortStationName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stationId = intent.extras?.getInt("stationId") ?: -1
        val stationShortName = stationId.toShortStationName()
        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                NavHost(
                    navController = navController,
                    startDestination = NavConstants.TRAIN_SCHEDULE
                ) {
                    composable(
                        NavConstants.TRAIN_SCHEDULE,
                    ) {

                        val viewModel: TrainScheduleViewModel = hiltViewModel()

                        UpcomingTrainsScreen(
                            state = viewModel.state.value
                        ) {
                            navController.navigate("${NavConstants.SET_TRAIN_ALARM}/$it/${stationShortName}")
                        }
                        LaunchedEffect(id) { viewModel.getScheduleForStation(stationId) }

                    }

                    composable(
                        NavConstants.SET_TRAIN_ALARM_ROUTE,
                        arguments = listOf(
                            navArgument(NavConstants.SET_TRAIN_ALARM_TIME) {
                                type = NavType.StringType
                            },
                            navArgument(NavConstants.SET_TRAIN_ALARM_STATION) {
                                type = NavType.StringType
                            }
                        )
                    ) { backStackEntry ->

                        val (stationName, time) = backStackEntry.arguments?.run {
                            Pair(
                                getString(NavConstants.SET_TRAIN_ALARM_STATION) ?: "",
                                getString(NavConstants.SET_TRAIN_ALARM_TIME) ?: ""
                            )
                        } ?: Pair("", "")
                        val totalMinutes = time.toMinutes()
                        val hours = totalMinutes.floorDiv(60)
                        val minutes = totalMinutes % 60
                        SetAlarmScreen(
                            stationName = stationShortName,
                            totalMinutes
                        ) {
                            val alarmTime = Calendar.getInstance().apply {
                                set(Calendar.HOUR_OF_DAY, hours)
                                set(Calendar.MINUTE, minutes)
                                add(Calendar.MINUTE, -it)
                            }
                            launchAlarmIntent(
                                alarmTime,
                                "${stationName.toFullStationName()} departure"
                            )

                        }
                    }
                }
            }
        }

    }

    private fun launchAlarmIntent(alarmTime: Calendar, msg: String) {
        val i = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, msg)
            putExtra(
                AlarmClock.EXTRA_HOUR,
                alarmTime.get(Calendar.HOUR_OF_DAY)
            )
            putExtra(
                AlarmClock.EXTRA_MINUTES,
                alarmTime.get(Calendar.MINUTE)
            )
        }
        startActivity(i)

    }
}