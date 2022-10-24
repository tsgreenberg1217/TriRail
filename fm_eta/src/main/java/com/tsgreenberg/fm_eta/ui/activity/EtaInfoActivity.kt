package com.tsgreenberg.fm_eta.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.wear.compose.material.MaterialTheme
import com.tsgreenberg.core.navigation.NavConstants
import com.tsgreenberg.core.navigation.NavConstants.STATION_INFO_ROUTE
import com.tsgreenberg.core.navigation.TriRailNav
import com.tsgreenberg.fm_eta.di.EtaInfoNavigationQualifier
import com.tsgreenberg.fm_eta.navigation.EtaNavigationAction
import com.tsgreenberg.fm_eta.ui.screens.EtaScreen
import com.tsgreenberg.fm_eta.ui.viewmodels.TrainArrivalViewModel
import com.tsgreenberg.ui_components.toShortStationName
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class EtaInfoActivity : ComponentActivity() {

    @Inject
    @EtaInfoNavigationQualifier
    lateinit var triRailNav: TriRailNav

    lateinit var navAction: EtaNavigationAction.GoToStationSchedule

    private fun goToSchedule() {
        triRailNav.navigate(navAction)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("TEST LOGS", "Lifecycle created")

        val stationId =
            intent.extras
                ?.getInt("StationInfo") ?: -1

        navAction = EtaNavigationAction.GoToStationSchedule(stationId)

        setContent {
            MaterialTheme {
                NavHost(
                    navController = rememberNavController(),
                    startDestination = STATION_INFO_ROUTE
                ) {
                    composable(
                        STATION_INFO_ROUTE, listOf(navArgument(NavConstants.STATION_ID) {
                            type = NavType.IntType
                            defaultValue = stationId
                        })
                    ) {
                        val viewModel: TrainArrivalViewModel = hiltViewModel()
                        EtaScreen(
                            shortName = stationId.toShortStationName(),
                            state = viewModel.state.value,
                            refresh = viewModel::initialRefreshRequest,
                            goToTrainSchedule = ::goToSchedule,
                        )
                    }
                }
            }
        }
    }


}