package com.tsgreenberg.eta_info.models

import com.google.gson.annotations.SerializedName
import com.tsgreenberg.ui_components.toMinutes

data class GetVehicleResponse(
    @SerializedName("get_vehicles") val vehicles: List<Vehicle>
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
    @SerializedName("directionAbbr") val direction: String,
    val minutesToNextStopDTOS: List<StopEtaInfoDTO>
)


data class StopEtaInfoDTO(
    val id: String,
    val enRoute: List<EnRouteInfo>
)



fun GetStopEtaResponse.toArrivalMap(): Map<String, TrainArrival> {
    val southBound = mutableListOf<EnRouteInfo>()
    val northBound = mutableListOf<EnRouteInfo>()

    etaDTOS.forEach { stops ->
        stops.enRoute.forEach {
            with(if (it.isNorthBound()) northBound else southBound) { add(it) }
        }

    }
    southBound.sortBy { it.minutes }
    northBound.sortBy { it.minutes }

    return mutableMapOf<String, TrainArrival>().apply {
        put("North", northBound.firstOrNull()?.toEstArrival() ?: TrainArrival.NoInformation)
        put("South", southBound.firstOrNull()?.toEstArrival() ?: TrainArrival.NoInformation)
    }
}

fun EnRouteInfo.isNorthBound(): Boolean = direction == "North"

fun EnRouteInfo.toEstArrival(): TrainArrival.EstimatedArrival = TrainArrival.EstimatedArrival(
    info = minutes,
    trainId = scheduleNumber,
    status = status,
    trackNumber = track
)

data class EnRouteInfo(
    val blockID: Int,
    val stopID: Int,
    val patternStopID: Int,
    val timePoint: Int,
    val minutes: Int,
    val time: String,
    val schedule: String,
    val status: String,
    val scheduleNumber: String,
    val statuscolor: String?,
    val track: Int,
    val direction: String,
    val directionAbbr: String,
    val equipmentID: String,
    val routeID: Int
)

data class GetStopEtaResponse(
    @SerializedName("get_stop_etas") val etaDTOS: List<StopEtaInfoDTO>
)

data class UiTrainSchedule(
    val stationId: Int,
    val trainId: Int,
    val direction: String,
    val timeString: String,
    val timeInMins: Int,
    val isWeekday: Boolean
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