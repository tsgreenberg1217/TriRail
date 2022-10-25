package com.tsgreenberg.core.navigation

abstract class TriRailRootAction

object NavConstants {
    const val STATION_ID = "station_id"
    const val STATION_INFO = "station_info"
    const val SET_TRAIN_ALARM = "set_train_alarm"
    const val SET_TRAIN_ALARM_TIME = "alarm_time"
    const val SET_TRAIN_ALARM_ROUTE = "$SET_TRAIN_ALARM/{$SET_TRAIN_ALARM_TIME}/{$STATION_ID}"
    const val STATION_INFO_ROUTE = "${STATION_INFO}/{${STATION_ID}}"
    const val STATION_LIST = "station_list"
    const val ETA = "eta"
    const val TRAIN_SCHEDULE = "TRAIN_SCHEDULE/{$STATION_ID}"
    const val CHOOSE_STATION = "choose_station"
}