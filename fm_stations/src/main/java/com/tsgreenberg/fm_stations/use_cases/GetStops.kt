package com.tsgreenberg.fm_stations.use_cases

import android.util.Log
import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.ProgressBarState
import com.tsgreenberg.fm_stations.models.UiStop
import com.tsgreenberg.fm_stations.models.toUiStop
import com.tsgreenberg.fm_stations.remote.StationsService
import com.tsgreenberg.stationlist.Station
import com.tsgreenberg.stationlist.StationQueries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


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
            Log.d("LOAD ISSUE", "fetching stations ${dbStations.isEmpty()}")
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