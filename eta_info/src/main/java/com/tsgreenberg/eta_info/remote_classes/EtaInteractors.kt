package com.tsgreenberg.eta_info.remote_classes

import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.ProgressBarState
import com.tsgreenberg.eta_info.mappers.EtaDtoMapper
import com.tsgreenberg.eta_info.models.toUiTrainSchedule
import com.tsgreenberg.ui_components.isWeekendHours
import kotlinx.coroutines.flow.flow
import java.util.*

data class EtaInteractors(
    val getEtaForStation: GetEtaForStation,
    val getTrainSchedulesForStation: GetTrainSchedulesForStation
) {
    companion object Factory {
        fun build(
            etaService: EtaService,
            trainsServices: TrainScheduleService,
            etaMapper: EtaDtoMapper
        ): EtaInteractors = EtaInteractors(
            getEtaForStation = GetEtaForStation(etaService, etaMapper),
            getTrainSchedulesForStation = GetTrainSchedulesForStation(trainsServices)
        )
    }
}


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
                .filter { it.timeInMins >= Calendar.getInstance().timeInMillis / 60000 }
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
    private val etaService: EtaService,
    private val mapper: EtaDtoMapper
) {
    fun execute(id: Int) = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            val response = etaService.getStopEtas(id)
            val data = mapper.invoke(response)
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(
                DataState.Error(e.localizedMessage.orEmpty())
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }

    }
}


//
//class GetEtaForStation(
//    private val etaService: EtaService
//) {
//    fun execute(id: Int) = flow {
//        try {
//            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
//            val response = etaService.getStopEtas(id)
//            emit(
//                DataState.Success(response.toArrivalMap())
//            )
//        } catch (e: Exception) {
//            emit(
//                DataState.Error(e.localizedMessage.orEmpty())
//            )
//        } finally {
//            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
//        }
//
//    }
//}

