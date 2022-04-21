package com.tsgreenberg.core.navigation

import com.tsgreenberg.core.navigation.NavConstants.CHOOSE_STATION
import com.tsgreenberg.core.navigation.NavConstants.ETA
import com.tsgreenberg.core.navigation.NavConstants.STATION_INFO_ROUTE
import com.tsgreenberg.core.navigation.NavConstants.STATION_LIST

sealed class TriRailRootAction(val route: String) {
    data class StationInfo(val station_id: Int) : TriRailRootAction(STATION_INFO_ROUTE) {
        companion object {
            const val intentKey = "StationInfo"
        }
    }

    object StationList : TriRailRootAction(STATION_LIST)
}

sealed class TriRailNavRouteAction(val route: String) {
    object Eta : TriRailNavRouteAction(ETA)
    object ChooseStation : TriRailNavRouteAction(CHOOSE_STATION)
}

object NavConstants {
    const val STATION_ID = "station_id"
    const val STATION_INFO = "station_info"
    const val STATION_INFO_ROUTE = "${STATION_INFO}/{${STATION_ID}}"
    const val STATION_LIST = "station_list"
    const val ETA = "eta"
    const val CHOOSE_STATION = "choose_station"
}