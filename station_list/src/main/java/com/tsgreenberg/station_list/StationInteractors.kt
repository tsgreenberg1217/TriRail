package com.tsgreenberg.station_list

import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.ProgressBarState
import com.tsgreenberg.stationlist.Station
import com.tsgreenberg.stationlist.StationQueries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


data class StationInteractors(
    val getStops: GetStops,
) {
    companion object Factory {
        fun build(
            stationService: StationsService,
            queries: StationQueries
        ): StationInteractors {
            return StationInteractors(
                getStops = GetStops(stationService, queries)
            )
        }
    }
}

fun List<Station>.toUiStop(): List<UiStop> = map {
    UiStop(id = it.id.toInt(), name = it.name, shortName = it.short_name)
}

class GetStops(
    private val service: StationsService,
    private val db: StationQueries
) {
    fun execute(): Flow<DataState<List<UiStop>>> = flow {
        try {
            emit(
                DataState.Loading(progressBarState = ProgressBarState.Loading)
            )

            val dbStations: List<Station> = db.selectAll().executeAsList()

            val data = if (dbStations.isEmpty()) {
                val response = service.getStops().get_stops.map { it.toUiStop() }
                db.transaction {
                    response.forEach {
                        db.insertStation(it.id.toLong(), it.name, it.shortName)
                    }
                }
                response.sortedBy { it.id }
            } else dbStations.toUiStop()

            emit(DataState.Success(data))


        } catch (e: Exception) {
            emit(
                DataState.Error(
                    e.message.orEmpty()
                )
            )
        } finally {
            emit(
                DataState.Loading(progressBarState = ProgressBarState.Idle)
            )
        }
    }
}