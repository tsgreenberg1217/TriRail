package com.tsgreenberg.station_list

import com.tsgreenberg.core.ProgressBarState

data class StationListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val stops:List<UiStop>? = null,
    val error:String? = null
)