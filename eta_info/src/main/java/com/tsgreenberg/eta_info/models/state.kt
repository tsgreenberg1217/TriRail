package com.tsgreenberg.eta_info.models

import com.tsgreenberg.core.ProgressBarState
import com.tsgreenberg.eta_info.UiStopEtaInfo
import com.tsgreenberg.eta_info.UiTrainSchedule

data class TrainInfoState(
    val etaProgressBarState: ProgressBarState = ProgressBarState.Idle,
    val eta: UiStopEtaInfo? = null,
)

data class TrainScheduleState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val trainSchedule: List<UiTrainSchedule> = listOf(),
)
