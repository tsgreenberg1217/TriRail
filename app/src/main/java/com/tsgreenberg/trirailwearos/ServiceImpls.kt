package com.tsgreenberg.trirailwearos

import com.tsgreenberg.eta_info.models.GetStopEtaResponseDto
import com.tsgreenberg.eta_info.models.GetVehicleResponseDto
import com.tsgreenberg.eta_info.remote_classes.EtaService
import com.tsgreenberg.station_list.GetStopsResponse
import com.tsgreenberg.station_list.StationsService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import javax.inject.Inject


class StationServiceImpl(
    private val client: HttpClient,
    private val builder: HttpRequestBuilder
) : StationsService {
    override suspend fun getStops(token: String): GetStopsResponse {
        return client.apply2Builder(builder) {
            url("${BuildConfig.API_URL}/service.php?service=get_stops")
            method = HttpMethod.Get
        }.body()
    }

}


class EtaServiceImpl(
    private val client: HttpClient,
    private val builder: HttpRequestBuilder
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
        return client.apply2Builder(builder) {
            url("${BuildConfig.API_URL}/service.php?service=get_stop_etas")
            parameter("stopIDs", stopId)
            method = HttpMethod.Get
        }.body()
    }
}


suspend fun HttpClient.apply2Builder(
    b: HttpRequestBuilder,
    block: HttpRequestBuilder.() -> Unit
): HttpResponse {
    return request(b.apply(block))
}
