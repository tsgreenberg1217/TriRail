package com.tsgreenberg.trirailwearos

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.tsgreenberg.fm_eta.models.GetStopEtaResponseDto
import com.tsgreenberg.fm_eta.models.GetVehicleResponseDto
import com.tsgreenberg.fm_eta.remote_classes.EtaService
import com.tsgreenberg.fm_schedule.models.TrainScheduleDto
import com.tsgreenberg.fm_schedule.remote.TrainScheduleService
import com.tsgreenberg.fm_stations.models.GetStopsResponse
import com.tsgreenberg.fm_stations.remote.StationsService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.tasks.await


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


internal class TrainScheduleServiceImpl(
    private val db: FirebaseFirestore
) : TrainScheduleService {
    override suspend fun getScheduleForStation(
        stationId: Int,
        isWeekday: Boolean
    ): List<TrainScheduleDto> {
        val r = db.collection("schedules")
            .whereEqualTo("station_id", stationId)
            .whereEqualTo("is_weekday", isWeekday)
            .get()
            .await()
        Log.d("","")
        return r.documents.map {
            TrainScheduleDto(
                stationId = it.get("station_id", Int::class.java) ?: -1,
                trainId = it.get("train_id", Int::class.java) ?: -1,
                direction = it.getString("direction").orEmpty(),
                time = it.getString("time").orEmpty(),
                isWeekday = it.getBoolean("is_weekday") ?: true
            )
        }

    }
}
