package com.tsgreenberg.fm_eta.models

import com.tsgreenberg.core.ProgressBarState

data class TrainInfoState(
    val refreshId:Int = -1,
    val etaProgressBarState: ProgressBarState = ProgressBarState.Start,
    val etaRefreshState: EtaRefreshState = EtaRefreshState.Enabled,
    val arrivalMap: Map<String, List<TrainArrival>> = mapOf(),
    val error: String? = null
)

sealed class EtaRefreshState {
    object Enabled : EtaRefreshState()
    data class Disabled(val timeDisabled: Long) : EtaRefreshState()

}


sealed class TrainArrival {
    data class EstimatedArrival(
        val info: Int,
        val trainId: String,
        val status: String?,
        val statusColor: String,
        val trackNumber: Int,
    ) : TrainArrival()

    object NoInformation : TrainArrival()
    object EndOfLine : TrainArrival()
}