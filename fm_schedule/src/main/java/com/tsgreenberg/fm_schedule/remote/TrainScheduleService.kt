package com.tsgreenberg.fm_schedule.remote

import com.tsgreenberg.fm_schedule.models.TrainScheduleDto

interface TrainScheduleService {
    suspend fun getScheduleForStation(stationId: Int, isWeekday:Boolean): List<TrainScheduleDto>
}