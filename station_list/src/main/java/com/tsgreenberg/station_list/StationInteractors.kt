package com.tsgreenberg.station_list

import com.tsgreenberg.core.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


data class StationInteractors(
    val getStops: GetStops,
) {
    companion object Factory {
        fun build(stationService: StationsService): StationInteractors {
            return StationInteractors(
                getStops = GetStops(stationService),
            )
        }
    }
}

class GetStops(
    private val stationService: StationsService
) {
    fun execute(): Flow<DataState<List<Stop>>> = flow {
        try {
            val response = stationService.getStops()
            emit(
                DataState.Success(data = response.stops)
            )
        } catch (e: Exception) {
            emit(
                DataState.Error(
                    e.message.orEmpty()
                )
            )
        }
    }
}