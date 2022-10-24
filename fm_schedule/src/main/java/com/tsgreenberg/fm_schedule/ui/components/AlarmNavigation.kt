package com.tsgreenberg.fm_schedule.ui.components

import android.content.Intent
import android.icu.util.Calendar
import android.provider.AlarmClock
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tsgreenberg.core.navigation.NavConstants
import com.tsgreenberg.fm_schedule.ui.screens.SetAlarmScreen
import com.tsgreenberg.ui_components.toFullStationName
import com.tsgreenberg.ui_components.toMinutes

fun NavGraphBuilder.navigateToAlarm(launchAlarmIntent: (Intent) -> Unit) {
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
            etaInMins = totalMinutes
        ) {
            val alarmTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hours)
                set(Calendar.MINUTE, minutes)
                add(Calendar.MINUTE, -it)
            }
            val i = createlarmIntent(
                alarmTime,
                "${stationName.toFullStationName()} departure"
            )

            launchAlarmIntent(i)

        }
    }
}

fun createlarmIntent(alarmTime: Calendar, msg: String): Intent {
    return Intent(AlarmClock.ACTION_SET_ALARM).apply {
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

}