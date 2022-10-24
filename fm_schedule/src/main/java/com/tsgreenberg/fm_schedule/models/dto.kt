package com.tsgreenberg.fm_schedule.models

import com.tsgreenberg.ui_components.toMinutes


data class TrainScheduleDto(
    val stationId: Int,
    val trainId: Int,
    val direction: String,
    val time: String,
    val isWeekday: Boolean
)

fun TrainScheduleDto.toUiTrainSchedule(): TrainSchedule = TrainSchedule(
    stationId = stationId,
    trainId = trainId,
    direction = direction,
    timeString = time,
    timeInMins = time.toMinutes(),
    isWeekday = isWeekday
)
