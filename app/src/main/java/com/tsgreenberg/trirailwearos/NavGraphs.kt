package com.tsgreenberg.trirailwearos

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.tsgreenberg.core.navigation.NavConstants
import com.tsgreenberg.core.navigation.TriRailNav
import com.tsgreenberg.station_info.EtaScreen
import com.tsgreenberg.station_list.ChooseStationNavigator

fun NavGraphBuilder.getStationList(triRailNav: TriRailNav) {
    navigation(
        startDestination = NavConstants.CHOOSE_STATION,
        route = NavConstants.STATION_LIST
    ) {
        composable(NavConstants.CHOOSE_STATION) {
            ChooseStationNavigator(triRailNav = triRailNav)
        }
    }
}


fun NavGraphBuilder.getStationDetail(triRailNav: TriRailNavImp) {
    navigation(
        startDestination = NavConstants.ETA,
        route = NavConstants.STATION_INFO_ROUTE
    ) {

        composable(
            route = NavConstants.ETA,
        ) {
            val entry = remember{
                triRailNav.navController.getBackStackEntry(NavConstants.STATION_INFO_ROUTE)
            }
            val id = entry.arguments?.getString("station_id") ?: "0"
            EtaScreen(triRailNav = triRailNav, id.toInt())
        }
    }
}