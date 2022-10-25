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
import com.tsgreenberg.ui_components.toShortStationName

fun NavGraphBuilder.navigateToAlarm(launchAlarmIntent: (Intent) -> Unit) {
    composable(
        NavConstants.SET_TRAIN_ALARM_ROUTE,
        arguments = listOf(
            navArgument(NavConstants.SET_TRAIN_ALARM_TIME) {
                type = NavType.StringType
            },
            navArgument(NavConstants.STATION_ID) {
                type = NavType.IntType
            }
        )
    ) { backStackEntry ->

        val (stationId, time) = backStackEntry.arguments?.run {
            Pair(
                getInt(NavConstants.STATION_ID),
                getString(NavConstants.SET_TRAIN_ALARM_TIME) ?: ""
            )
        } ?: Pair(-1, "")

        val stationShortName = stationId.toShortStationName()
        val totalMinutes = time.toMinutes()
        val hours = totalMinutes.floorDiv(60)
        val minutes = totalMinutes % 60
        SetAlarmScreen(stationShortName) {
            val alarmTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hours)
                set(Calendar.MINUTE, minutes)
                add(Calendar.MINUTE, -it)
            }
            val i = createAlarmIntent(
                alarmTime,
                "${stationShortName.toFullStationName()} departure"
            )

            launchAlarmIntent(i)

        }
    }
}

fun createAlarmIntent(alarmTime: Calendar, msg: String): Intent {
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