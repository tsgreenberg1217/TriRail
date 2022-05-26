package com.tsgreenberg.core.navigation

import com.tsgreenberg.core.navigation.NavConstants.CHOOSE_STATION
import com.tsgreenberg.core.navigation.NavConstants.ETA
import com.tsgreenberg.core.navigation.NavConstants.STATION_INFO_ROUTE
import com.tsgreenberg.core.navigation.NavConstants.STATION_LIST

sealed class TriRailRootAction(val route: String) {
    data class StationInfo(val station_id: Int, val shortName:String) : TriRailRootAction(STATION_INFO_ROUTE) {
        companion object {
            const val intentKey = "StationInfo"
            const val intentKeyName = "StationInfo_name"
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
    const val SET_TRAIN_ALARM = "set_train_alarm"
    const val SET_TRAIN_ALARM_TIME = "alarm_time"
    const val SET_TRAIN_ALARM_STATION = "alarm_station"
    const val SET_TRAIN_ALARM_ROUTE = "$SET_TRAIN_ALARM/{$SET_TRAIN_ALARM_TIME}/{$SET_TRAIN_ALARM_STATION}"
    const val STATION_INFO_ROUTE = "${STATION_INFO}/{${STATION_ID}}"
    const val STATION_LIST = "station_list"
    const val ETA = "eta"
    const val TRAIN_SCHEDULE = "TRAIN_SCHEDULE"
    const val CHOOSE_STATION = "choose_station"
}