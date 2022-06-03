package com.tsgreenberg.eta_info.remote_classes

import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.ProgressBarState
import com.tsgreenberg.eta_info.models.toArrivalMap
import com.tsgreenberg.eta_info.models.toUiTrainSchedule
import com.tsgreenberg.ui_components.isWeekendHours
import com.tsgreenberg.ui_components.toMinutes
import kotlinx.coroutines.flow.flow
import java.util.*

data class EtaInteractors(
    val getEtaForStation: GetEtaForStation,
    val getTrainSchedulesForStation: GetTrainSchedulesForStation
) {
    companion object Factory {
        fun build(
            etaService: EtaService,
            trainsServices: TrainScheduleService
        ): EtaInteractors = EtaInteractors(
            getEtaForStation = GetEtaForStation(etaService),
            getTrainSchedulesForStation = GetTrainSchedulesForStation(trainsServices)
        )
    }
}


class GetTrainSchedulesForStation(
    private val trainScheduleService: TrainScheduleService
) {
    fun execute(id: Int, direction: String) = flow {
        try {
            val nowInMinutes = Date().toMinutes()
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val response = trainScheduleService.getScheduleForStation(id, direction, Date().isWeekendHours().not())
                .map { it.toUiTrainSchedule() }
                .filter { it.timeInMins >= nowInMinutes }
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

class GetEtaForStation(
    private val etaService: EtaService
) {
    fun execute(id: Int) = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            val response = etaService.getStopEtas(id)
            emit(
                DataState.Success(response.toArrivalMap())
            )
        } catch (e: Exception) {
            emit(
                DataState.Error(e.localizedMessage.orEmpty())
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }

    }
}

