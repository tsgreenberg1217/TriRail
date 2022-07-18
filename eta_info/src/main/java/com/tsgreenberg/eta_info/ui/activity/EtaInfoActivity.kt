package com.tsgreenberg.eta_info.ui.activity

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.wear.compose.material.MaterialTheme
import com.tsgreenberg.core.navigation.NavConstants
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.core.navigation.TriRailRootAction
import com.tsgreenberg.eta_info.di.EtaInfoNavigationQualifier
import com.tsgreenberg.eta_info.models.EtaInfoViewModelCache
import com.tsgreenberg.eta_info.models.EtaRefreshState
import com.tsgreenberg.eta_info.ui.screens.EtaScreen
import com.tsgreenberg.eta_info.ui.screens.SetAlarmScreen
import com.tsgreenberg.eta_info.ui.screens.UpcomingTrainsScreen
import com.tsgreenberg.eta_info.ui.viewmodels.TrainArrivalViewModel
import com.tsgreenberg.eta_info.ui.viewmodels.TrainScheduleViewModel
import com.tsgreenberg.ui_components.toFullStationName
import com.tsgreenberg.ui_components.toMinutes
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class EtaInfoActivity : ComponentActivity() {

    @Inject
    @EtaInfoNavigationQualifier
    lateinit var triRailNav: TriRailNavImplementor<NavHostController>

    @Inject
    lateinit var viewModelCache: EtaInfoViewModelCache

    private val viewModel: TrainArrivalViewModel by viewModels()

    private fun setViewModelDirection(direction: String){
        viewModel.setTrainDirection(direction)
        triRailNav.navController.navigate("${NavConstants.STATION_INFO}/$direction")
    }

    private fun initialRefreshRequest(){
        viewModel.run {
            if(state.value.etaRefreshState is EtaRefreshState.Enabled){
                refresh()
                setRefreshState(
                    EtaRefreshState.Disabled(Calendar.getInstance().timeInMillis)
                )
                setEnableTimer(60)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("TEST LOGS", "Lifecycle created")

        viewModelCache.apply {
            stationId =
                intent.extras
                    ?.getInt(TriRailRootAction.StationInfo.intentKey) ?: -1
            stationShortName =
                intent.extras
                    ?.getString(TriRailRootAction.StationInfo.intentKeyName) ?: ""
        }

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                triRailNav.navController = navController
                NavHost(
                    navController = navController,
                    startDestination = NavConstants.ETA
                ) {

                    composable(NavConstants.ETA) {
                        EtaScreen(
                            shortName = viewModelCache.stationShortName,
                            state = viewModel.state.value,
                            refresh = ::initialRefreshRequest,
                            goToTrainSchedule = this@EtaInfoActivity::setViewModelDirection,
                        )
                    }

                    composable(
                        NavConstants.STATION_INFO_ROUTE,
                        arguments = listOf(navArgument("station_id") {
                            type = NavType.StringType
                            defaultValue = "S"
                        })
                    ) {
                        val viewModel: TrainScheduleViewModel = hiltViewModel()
                        UpcomingTrainsScreen(
                            stationName = viewModelCache.stationShortName,
                            state = viewModel.state.value
                        ) {
                            navController.navigate("${NavConstants.SET_TRAIN_ALARM}/$it/${viewModelCache.stationShortName}")
                        }

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
                            stationName = viewModelCache.stationShortName,
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

    override fun onResume() {
        super.onResume()
        Log.d("TEST LOGS", "Lifecycle resumed: coroute running = ${viewModel.job?.isActive ?: false}")

    }

    override fun onPause() {
        super.onPause()
        Log.d("TEST LOGS", "Lifecycle paused")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TEST LOGS", "Lifecycle stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TEST LOGS", "Lifecycle destoryed")
    }

    override fun isFinishing(): Boolean {
        Log.d("TEST LOGS", "Lifecycle finishing")
        return super.isFinishing()
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




//composable{
//            val entry = remember{
//                triRailNav.navController.getBackStackEntry(NavConstants.STATION_INFO_ROUTE)
//            }
//            val id = entry.arguments?.getString("station_id") ?: "0"
//}