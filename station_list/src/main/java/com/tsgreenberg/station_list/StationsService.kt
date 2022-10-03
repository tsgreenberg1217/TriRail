package com.tsgreenberg.station_list

interface StationsService {

    suspend fun getStops(
        token: String = "TESTING"
    ): GetStopsResponse

}