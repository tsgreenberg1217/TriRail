package com.tsgreenberg.fm_schedule.use_cases

import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.ProgressBarState
import com.tsgreenberg.schedule.models.toUiTrainSchedule
import com.tsgreenberg.schedule.remote.TrainScheduleService
import com.tsgreenberg.schedule.utils.minutesFromMidnight
import com.tsgreenberg.ui_components.isWeekendHours
import kotlinx.coroutines.flow.flow
import java.util.*

class GetTrainSchedulesForStation(
    private val trainScheduleService: TrainScheduleService
) {
    fun execute(id: Int) = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val response = trainScheduleService.getScheduleForStation(
                id,
                Date().isWeekendHours().not()
            )
                .map { it.toUiTrainSchedule() }
                .filter { it.timeInMins >= Calendar.getInstance().minutesFromMidnight() }
                .sortedBy { it.timeInMins }

            emit(DataState.Success(response))
        } catch (e: Exception) {
            emit(
                DataState.Error(e.localizedMessage.orEmpty())
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }
}