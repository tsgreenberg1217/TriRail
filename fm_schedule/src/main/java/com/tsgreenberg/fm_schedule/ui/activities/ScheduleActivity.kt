package com.tsgreenberg.fm_schedule.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.MaterialTheme
import com.tsgreenberg.core.navigation.NavConstants
import com.tsgreenberg.fm_schedule.ui.components.navigateToAlarm
import com.tsgreenberg.fm_schedule.ui.components.navigateToSchedule
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stationId = intent.extras?.getInt("stationId") ?: -1
        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                NavHost(
                    navController = navController, startDestination = NavConstants.TRAIN_SCHEDULE
                ) {
                    navigateToSchedule(stationId) { time, id ->
                        navController.navigate("${NavConstants.SET_TRAIN_ALARM}/$time/$id")
                    }
                    navigateToAlarm { startActivity(it) }
                }
            }
        }

    }
}