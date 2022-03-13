package com.tsgreenberg.station_list

import retrofit2.http.GET
import retrofit2.http.Query

interface StationsService {

    @GET("/service.php?service=get_stops")
    suspend fun getStops(
        @Query("token") token: String = "TESTING"
    ): GetStopsResponse

}