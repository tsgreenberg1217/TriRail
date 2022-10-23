package com.tsgreenberg.fm_stations.models

import com.tsgreenberg.core.ProgressBarState

data class StationListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val stops:List<UiStop>? = null,
    val error:String? = null
)