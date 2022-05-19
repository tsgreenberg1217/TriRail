package com.tsgreenberg.eta_info.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.tsgreenberg.eta_info.ui.*
import com.tsgreenberg.eta_info.ui.viewmodels.StationDetailViewModel
import com.tsgreenberg.eta_info.ui.viewmodels.TrainScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EtaInfoActivity : ComponentActivity() {

    @Inject
    @EtaInfoNavigationQualifier
    lateinit var triRailNav: TriRailNavImplementor<NavHostController>

    @Inject
    lateinit var viewModelCache: EtaInfoViewModelCache

    var stationId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelCache.apply {
            stationId =
                intent.extras
                    ?.getInt(TriRailRootAction.StationInfo.intentKey) ?: -1
            stationName =
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
                        val viewModel: StationDetailViewModel = hiltViewModel()
                        EtaScreen(
                            shortName = viewModelCache.stationName,
                            viewModel.state.value,
                            refresh = viewModel::refresh,
                            goToTrainSchedule = {
                                viewModelCache.trainDirection = it
                                navController.navigate("${NavConstants.STATION_INFO}/$it")
                            }
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
                            stationName = viewModelCache.stationName,
                            state = viewModel.state.value
                        )

                    }
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