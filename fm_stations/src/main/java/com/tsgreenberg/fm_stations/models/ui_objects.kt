package com.tsgreenberg.fm_stations.models


data class UiStop(
    val id: Int,
    val name: String,
    val shortName: String
)

data class GetVehicleResponse(
    val vehicles: List<Vehicle>
)

data class Vehicle(
    val routeID: Int,
    val patternID: Int,
    val equipmentID: String,
    val tripID: String?,
    val lat: Double,
    val lng: Double,
    val load: Int,
    val capacity: Int,
    val blockID: Int,
    val nextStopID: Int,
    val nextStopETA: Int,
    val nextPatternStopID: Int,
    val h: Int,
    val scheduleNumber: String,
    val inService: Int,
    val onSchedule: Int,
    val vehicleType: String,
    val trainID: Int,
    val receiveTime: Long,
    val deadHead: String,
    val aID: String,
    val direction: String,
    val minutesToNextStops: List<StopEtaInfo>
)


data class StopEtaInfo(
    val blockID: Int,
    val stopID: Int,
    val patternStopID: Int,
    val minutes: Int,
    val time: String,
    val schedule: String,
    val status: String,
    val scheduleNumber: String,
    val statuscolor: String,
    val track: Int,
    val direction: String,
    val equipmentID: String,
    val routeID: Int
)


data class GetStopEtaResponse(
    val etas: List<StopEtaInfo>
)