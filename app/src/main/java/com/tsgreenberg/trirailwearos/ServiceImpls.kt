package com.tsgreenberg.trirailwearos

import com.tsgreenberg.eta_info.models.GetStopEtaResponseDto
import com.tsgreenberg.eta_info.models.GetVehicleResponseDto
import com.tsgreenberg.eta_info.remote_classes.EtaService
import com.tsgreenberg.station_list.GetStopsResponse
import com.tsgreenberg.station_list.StationsService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject


class StationServiceImpl(
    private val client: HttpClient
) : StationsService {
    override suspend fun getStops(token: String): GetStopsResponse {
        return client.get {
            url("${BuildConfig.API_URL}/service.php?service=get_stops")
            parameter("token", "TESTING")
        }.body()
    }

}


class EtaServiceImpl(
    private val client: HttpClient
) : EtaService {
    override suspend fun getVehicles(
        hasETAData: Int,
        isEtaOrdered: Int,
        isInService: Int,
        hasDirections: Int,
        token: String
    ): GetVehicleResponseDto {
        TODO("Not yet implemented")
    }

    override suspend fun getStopEtas(stopId: Int, token: String): GetStopEtaResponseDto {
        return client.get {
            url("${BuildConfig.API_URL}/service.php?service=get_stop_etas")
            parameter("token", "TESTING")
            parameter("stopIDs", stopId)
        }.body()
    }

}
