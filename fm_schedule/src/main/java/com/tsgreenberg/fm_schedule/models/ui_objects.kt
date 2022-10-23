package com.tsgreenberg.schedule.models

data class TrainSchedule(
    val stationId: Int,
    val trainId: Int,
    val direction: String,
    val timeString: String,
    val timeInMins: Int,
    val isWeekday: Boolean
)
