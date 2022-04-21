package com.tsgreenberg.eta_info

import retrofit2.http.GET
import retrofit2.http.Query

interface EtaService {
    @GET("/service.php?service=get_vehicles")
    suspend fun getVehicles(
        @Query("includeETAData") hasETAData: Int = 1,
        @Query("orderedETAArray") isEtaOrdered: Int = 1,
        @Query("inService") isInService: Int = 0,
        @Query("includeDirectionsData") hasDirections: Int = 1,
        @Query("token") token: String = "TESTING"
    ): GetVehicleResponse


    @GET("/service.php?service=get_stop_etas")
    suspend fun getStopEtas(
        @Query("stopIDs") stopId: Int,
        @Query("token") token: String = "TESTING"
    ): GetStopEtaResponse
}