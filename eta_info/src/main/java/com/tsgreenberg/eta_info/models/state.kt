package com.tsgreenberg.eta_info.models

import com.tsgreenberg.core.ProgressBarState

data class TrainInfoState(
    val etaProgressBarState: ProgressBarState = ProgressBarState.Idle,
    val arrivalMap: Map<String, TrainArrival>? = null,
)

data class TrainScheduleState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val trainSchedule: List<UiTrainSchedule>? = null,
)

sealed class TrainArrival {
    data class EstimatedArrival(
        val info: Int,
        val trainId: String,
        val status: String,
        val trackNumber: Int
    ) : TrainArrival()

//    data class ScheduledArrival(
//        val info: String,
//        val trainId: Int,
//    ) : TrainArrival()

//    object NoService : TrainArrival()
    object NoInformation : TrainArrival()
}