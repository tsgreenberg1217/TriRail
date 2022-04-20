package com.tsgreenberg.station_list

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.tsgreenberg.core.navigation.NavConstants
import com.tsgreenberg.core.navigation.TriRailNav
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.station_list.di.StationListNavigationQualifier
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StationListActivity : ComponentActivity() {

    @Inject
    @StationListNavigationQualifier
    lateinit var triRailNav: TriRailNavImplementor<NavHostController>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            triRailNav.navController = navController

            val navState = navController.currentBackStackEntryAsState()
            Log.d("TRI RAIL", "current destination is ${navState.value?.destination?.route}")

            NavHost(
                navController = triRailNav.navController,
                startDestination = NavConstants.STATION_LIST
            ) {
                with(triRailNav) {
                    getStationList(this)
                }
            }
        }
    }
}


internal fun NavGraphBuilder.getStationList(triRailNav: TriRailNav) {
    navigation(
        startDestination = NavConstants.CHOOSE_STATION,
        route = NavConstants.STATION_LIST
    ) {
        composable(NavConstants.CHOOSE_STATION) {
            ChooseStationNavigator(triRailNav = triRailNav)
        }
    }
}
