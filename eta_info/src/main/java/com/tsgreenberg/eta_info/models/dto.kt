package com.tsgreenberg.eta_info.models

import com.tsgreenberg.ui_components.toMinutes
import kotlinx.serialization.Serializable

data class GetVehicleResponseDto(
    val vehicles: List<VehicleDto>
)

data class VehicleDto(
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
    val minutesToNextStopDTOS: List<StopEtaInfoDTO>
)


@Serializable
data class StopEtaInfoDTO(
    val id: String,
    val enRoute: List<EnRouteInfoDTO>
)

@Serializable
data class EnRouteInfoDTO(
    val minutes: Int,
    val status: String,
    val scheduleNumber: String,
    val statusColor: String? = null,
    val track: Int,
    val direction: String,
    val stopID:Int,
)

@Serializable
data class GetStopEtaResponseDto(
    val get_stop_etas: List<StopEtaInfoDTO>,
)