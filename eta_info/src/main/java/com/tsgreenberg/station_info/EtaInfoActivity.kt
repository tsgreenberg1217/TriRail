package com.tsgreenberg.station_info

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.station_info.di.EtaInfoNavigationQualifier
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EtaInfoActivity : ComponentActivity() {

    @Inject
    @EtaInfoNavigationQualifier
    lateinit var triRailNav: TriRailNavImplementor<NavHostController>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            triRailNav.navController = navController

            val navState = navController.currentBackStackEntryAsState()
            Log.d("TRI RAIL", "current destination is ${navState.value?.destination?.route}")


            EtaScreen(triRailNav = triRailNav, 12)
//            NavHost(
//                navController = triRailNav.navController,
//                startDestination = NavConstants.STATION_INFO_ROUTE
//            ) {
//                with(triRailNav) {
//                    getStationDetail(this)
//                }
//            }
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