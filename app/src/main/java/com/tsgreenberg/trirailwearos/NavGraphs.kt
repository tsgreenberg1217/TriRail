package com.tsgreenberg.trirailwearos

import android.annotation.SuppressLint
import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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


@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.getStationDetail(triRailNav: TriRailNavImp) {
    navigation(
        startDestination = NavConstants.ETA,
        route = NavConstants.STATION_INFO_ROUTE
    ) {

        composable(
            route = NavConstants.ETA,
        ) {

            val id = triRailNav.navController.getBackStackEntry(NavConstants.STATION_INFO_ROUTE).arguments?.getString("station_id") ?: "0"
            Log.d("TRI RAIL", "id from arg is $id")
            EtaScreen(triRailNav = triRailNav, id.toInt())
        }
    }
}