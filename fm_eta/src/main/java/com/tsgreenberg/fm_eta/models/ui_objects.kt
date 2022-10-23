package com.tsgreenberg.fm_eta.models


data class ArrivalData(
    val stopId: Int,
    val data:List<Arrival>
)
data class Arrival(
    val info: Int = -1,
    val trainId: String,
    val status: String,
    val statusColor: String,
    val trackNumber: Int,
    val direction: String
)

