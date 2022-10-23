package com.tsgreenberg.schedule.remote

import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.ProgressBarState
import com.tsgreenberg.schedule.models.TrainScheduleDto
import com.tsgreenberg.schedule.models.toUiTrainSchedule
import com.tsgreenberg.schedule.utils.minutesFromMidnight
import com.tsgreenberg.ui_components.isWeekendHours
import kotlinx.coroutines.flow.flow
import java.util.*

interface TrainScheduleService {
    suspend fun getScheduleForStation(stationId: Int, isWeekday:Boolean): List<TrainScheduleDto>
}