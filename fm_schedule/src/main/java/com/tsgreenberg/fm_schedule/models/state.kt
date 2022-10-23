package com.tsgreenberg.schedule.models

import com.tsgreenberg.core.ProgressBarState


data class TrainScheduleState(
    val progressBarState: ProgressBarState = ProgressBarState.Start,
    val trainSchedule: Map<String,List<TrainSchedule>> = mapOf(),
    val error: String? = null
)