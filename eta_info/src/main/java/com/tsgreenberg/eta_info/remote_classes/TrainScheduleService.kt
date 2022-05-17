package com.tsgreenberg.eta_info.remote_classes

import com.google.firebase.firestore.FirebaseFirestore
import com.tsgreenberg.eta_info.TrainScheduleDto
import kotlinx.coroutines.tasks.await

interface TrainScheduleService {
    suspend fun getScheduleForStation(stationId: Int, direction: String): List<TrainScheduleDto>
}

internal class TrainScheduleServiceImpl(
    private val db: FirebaseFirestore
) : TrainScheduleService {
    override suspend fun getScheduleForStation(
        stationId: Int,
        direction: String
    ): List<TrainScheduleDto> {
        val r = db.collection("schedules")
            .whereEqualTo("station_id", stationId)
            .whereEqualTo("is_weekday", true)
            .whereEqualTo("direction", direction)
            .get()
            .await()
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