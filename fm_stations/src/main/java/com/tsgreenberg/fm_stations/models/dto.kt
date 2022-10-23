package com.tsgreenberg.fm_stations.models

import com.tsgreenberg.stationlist.Station
import kotlinx.serialization.Serializable


@Serializable
data class GetStopsResponse(
    val get_stops: List<StopDto>
)

@Serializable
data class StopDto(
    val id: Int,
    val name: String,
    val shortName: String
//    val rid: Int,
//    val lat: Double,
//    val lng: Double,
//    val extID: String,
)


fun StopDto.toUiStop(): UiStop = UiStop(
    id = this.id,
    name = this.name.replace("Station","").trim(),
    shortName = this.shortName
)


fun List<Station>.toUiStop(): List<UiStop> = map {
    UiStop(id = it.id.toInt(), name = it.name, shortName = it.short_name)
}
