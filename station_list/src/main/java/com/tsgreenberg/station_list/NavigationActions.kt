package com.tsgreenberg.station_list

import com.tsgreenberg.core.navigation.NavConstants
import com.tsgreenberg.core.navigation.TriRailRootAction

sealed class StationListNavigation(route: String) : TriRailRootAction(route){
    data class StationEtaInfo(val station_id: Int, val shortName: String) : StationListNavigation(NavConstants.STATION_INFO_ROUTE){
        companion object {
            const val intentKey = "StationInfo"
            const val intentKeyName = "StationInfo_name"
        }
    }
}
