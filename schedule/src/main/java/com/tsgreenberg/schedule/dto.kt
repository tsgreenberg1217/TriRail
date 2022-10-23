package com.tsgreenberg.schedule

import com.tsgreenberg.core.ProgressBarState
import com.tsgreenberg.ui_components.toMinutes


data class TrainScheduleDto(
    val stationId: Int,
    val trainId: Int,
    val direction: String,
    val time: String,
    val isWeekday: Boolean
)

fun TrainScheduleDto.toUiTrainSchedule(): UiTrainSchedule = UiTrainSchedule(
    stationId = stationId,
    trainId = trainId,
    direction = direction,
    timeString = time,
    timeInMins = time.toMinutes(),
    isWeekday = isWeekday
)

data class UiTrainSchedule(
    val stationId: Int,
    val trainId: Int,
    val direction: String,
    val timeString: String,
    val timeInMins: Int,
    val isWeekday: Boolean
)

data class TrainScheduleState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val trainSchedule: Map<String,List<UiTrainSchedule>> = mapOf(),
    val error: String? = null
)