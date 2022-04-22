package com.tsgreenberg.eta_info.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.MaterialTheme
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.core.navigation.TriRailRootAction
import com.tsgreenberg.eta_info.di.EtaInfoNavigationQualifier
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EtaInfoActivity : ComponentActivity() {

    @Inject
    @EtaInfoNavigationQualifier
    lateinit var triRailNav: TriRailNavImplementor<NavHostController>

    private val viewModel: StationDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stationId = intent.extras?.getInt(TriRailRootAction.StationInfo.intentKey)
        stationId?.let { viewModel.setStationEta(it) }
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                triRailNav.navController = navController
                EtaScreen(
                    triRailNav = triRailNav,
                    state = viewModel.state.value
                )
            }
        }
    }
}


//internal fun NavGraphBuilder.getStationDetail(triRailNav: TriRailNavImplementor<NavHostController>) {
//    navigation(
//        startDestination = NavConstants.ETA,
//        route = NavConstants.STATION_INFO_ROUTE
//    ) {
//
//        composable(
//            route = NavConstants.ETA,
//        ) {
//            val entry = remember{
//                triRailNav.navController.getBackStackEntry(NavConstants.STATION_INFO_ROUTE)
//            }
//            val id = entry.arguments?.getString("station_id") ?: "0"
//            EtaScreen(triRailNav = triRailNav, id.toInt())
//        }
//    }
//}