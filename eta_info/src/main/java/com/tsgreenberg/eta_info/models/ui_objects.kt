package com.tsgreenberg.eta_info.models

data class UiTrainSchedule(
    val stationId: Int,
    val trainId: Int,
    val direction: String,
    val timeString: String,
    val timeInMins: Int,
    val isWeekday: Boolean
)

data class ArrivalData(
    val info: Int = -1,
    val trainId: String,
    val status: String,
    val statusColor: String,
    val trackNumber: Int,
    val stopId:Int,
    val direction: String
)

