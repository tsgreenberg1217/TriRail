package com.tsgreenberg.trirailwearos

import android.util.Log
import androidx.navigation.NavHostController
import com.tsgreenberg.core.navigation.NavConstants
import com.tsgreenberg.core.navigation.TriRailNav
import com.tsgreenberg.core.navigation.TriRailRootAction
import javax.inject.Inject

class TriRailNavImp @Inject constructor() : TriRailNav {
    lateinit var navController: NavHostController

    override fun navigate(routeAction: TriRailRootAction) {
        when (routeAction) {
            is TriRailRootAction.StationInfo -> {
                Log.d("TRI RAIL", "id from class is ${routeAction.station_id}")
                navController.navigate("${NavConstants.STATION_INFO}/${routeAction.station_id}")
            }
            is TriRailRootAction.StationList -> {

            }
        }

    }

//    override fun <T : Any> getArgsFromBackstack(route: String, key: String): T {
//        return navController.getBackStackEntry(route).arguments?.getInt(key) as T!!
//    }


    fun setController(controller: NavHostController) {
        navController = controller
    }

    fun getController(): NavHostController = navController


}