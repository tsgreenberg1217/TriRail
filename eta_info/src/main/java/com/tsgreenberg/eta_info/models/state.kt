package com.tsgreenberg.eta_info.models

import com.tsgreenberg.core.ProgressBarState
import com.tsgreenberg.eta_info.UiStopEtaInfo

data class EtaStationState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val eta: UiStopEtaInfo? = null,
)
