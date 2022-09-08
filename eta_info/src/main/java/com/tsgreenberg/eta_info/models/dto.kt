package com.tsgreenberg.eta_info.models

import com.google.gson.annotations.SerializedName
import com.tsgreenberg.ui_components.toMinutes

data class GetVehicleResponseDto(
    @SerializedName("get_vehicles") val vehicles: List<VehicleDto>
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
    @SerializedName("directionAbbr") val direction: String,
    val minutesToNextStopDTOS: List<StopEtaInfoDTO>
)


data class StopEtaInfoDTO(
    val id: String,
    val enRoute: List<EnRouteInfoDTO>
)

data class EnRouteInfoDTO(
    val minutes: Int,
    val status: String,
    val scheduleNumber: String,
    val statusColor: String?,
    val track: Int,
    val direction: String,
    val stopId:Int,
)

data class GetStopEtaResponseDto(
    @SerializedName("get_stop_etas") val etaDTOS: List<StopEtaInfoDTO>
)

data class TrainScheduleDto(
    val stationId: Int,
    val trainId: Int,
    val direction: String,
    val time: String,
    val isWeekday: Boolean
)

fun TrainScheduleDto.toUiTrainSchedule(): UiTrainSchedule = UiTrainSchedule(
    stationId = stationId,
    trainId = trainId,
    direction = direction,
    timeString = time,
    timeInMins = time.toMinutes(),
    isWeekday = isWeekday
)