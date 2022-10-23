package com.tsgreenberg.station_list

import com.tsgreenberg.core.navigation.TriRailRootAction

sealed class StationListNavigation : TriRailRootAction(){
    data class StationEtaInfo(val station_id: Int, val shortName: String) : StationListNavigation(){
        companion object {
            const val intentKey = "StationInfo"
            const val intentKeyName = "StationInfo_name"
        }
    }
}
