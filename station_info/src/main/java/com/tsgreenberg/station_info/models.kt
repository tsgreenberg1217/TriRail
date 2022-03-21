package com.tsgreenberg.station_info

import android.util.Log
import com.google.gson.annotations.SerializedName

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


//data class StopEtaInfo(
//    val blockID: Int,
//    val stopID: Int,
//    val patternStopID: Int,
//    val minutes: Int,
//    val time: String,
//    val schedule: String,
//    val status: String,
//    val scheduleNumber: String,
//    val statuscolor: String,
//    val track: Int,
//    @SerializedName("directionAbbr") val direction: String,
//    val equipmentID: String,
//    val routeID: Int
//)

data class StopEtaInfoDTO(
    val id: String,
    val enRoute: List<EnRouteInfo>
)

data class UiStopEtaInfo(
    val etaMap: Map<String, List<EnRouteInfo>>
)


fun GetStopEtaResponse.toUIStopEta(): UiStopEtaInfo = UiStopEtaInfo(
    etaMap = if (etaDTOS.isEmpty()) mapOf(
        "North" to listOf(),
        "South" to listOf()
    ) else etaDTOS.first().toUIMap()
)


fun StopEtaInfoDTO.toUIMap(): Map<String, List<EnRouteInfo>> = if (enRoute.isEmpty()) mapOf(
    "North" to listOf(),
    "South" to listOf()
) else enRoute.getNextTrains()

fun List<EnRouteInfo>.getNextTrains(): Map<String, List<EnRouteInfo>> {
    Log.d("TRI RAIL", "list $this")
    val southBound = mutableListOf<EnRouteInfo>()
    val northBound = mutableListOf<EnRouteInfo>()

    forEach { enRoute ->
        with(if (enRoute.isNorthBound()) southBound else northBound) { add(enRoute) }
    }
    southBound.sortBy { it.minutes }
    northBound.sortBy { it.minutes }
    val northForMap = if (northBound.isEmpty()) listOf() else northBound.toList()
    val southForMap = if (southBound.isEmpty()) listOf() else northBound.toList()

    return mutableMapOf<String, List<EnRouteInfo>>().apply {
        put("North", northForMap)
        put("South", southForMap)
    }
}

fun EnRouteInfo.isNorthBound(): Boolean = direction == "North"
fun EnRouteInfo.isSouthBound(): Boolean = direction == "South"


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
    val statuscolor: String,
    val track: Int,
    val direction: String,
    val directionAbbr: String,
    val equipmentID: String,
    val routeID: Int
)

data class GetStopEtaResponse(
    @SerializedName("get_stop_etas") val etaDTOS: List<StopEtaInfoDTO>
)