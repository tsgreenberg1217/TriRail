package com.tsgreenberg.trirailwearos

import com.tsgreenberg.station_list.GetStopsResponse
import com.tsgreenberg.station_list.StationsService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject


class StationServiceKtorImpl(
    private val client:HttpClient
)  : StationsService {
    override suspend fun getStops(token: String): GetStopsResponse {
        return client.get {
            url("${BuildConfig.API_URL}/service.php?service=get_stops")
            parameter("token", "TESTING")
        }.body()
    }

}