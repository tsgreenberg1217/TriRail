package com.tsgreenberg.fm_stations.interactors

import com.tsgreenberg.fm_stations.use_cases.GetStops
import com.tsgreenberg.fm_stations.remote.StationsService
import com.tsgreenberg.stationlist.StationQueries

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
