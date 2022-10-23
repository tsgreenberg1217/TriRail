package com.tsgreenberg.eta_info.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.MaterialTheme
import com.tsgreenberg.core.navigation.TriRailNav
import com.tsgreenberg.eta_info.di.EtaInfoNavigationQualifier
import com.tsgreenberg.eta_info.navigation.EtaNavigationAction
import com.tsgreenberg.eta_info.ui.screens.EtaScreen
import com.tsgreenberg.eta_info.ui.viewmodels.TrainArrivalViewModel
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

        val stationShortName =
            intent.extras
                ?.getString("StationInfo_name") ?: ""

        setContent {
            MaterialTheme {
                val viewModel: TrainArrivalViewModel = hiltViewModel()
                EtaScreen(
                    shortName = stationShortName,
                    state = viewModel.state.value,
                    refresh = viewModel::initialRefreshRequest,
                    goToTrainSchedule = ::goToSchedule,
                )

                LaunchedEffect(key1 = stationId) {
                    viewModel.getEstTrainArrivals(stationId)
                }


            }
        }
    }



}


//composable{
//            val entry = remember{
//                triRailNav.navController.getBackStackEntry(NavConstants.STATION_INFO_ROUTE)
//            }
//            val id = entry.arguments?.getString("station_id") ?: "0"
//}