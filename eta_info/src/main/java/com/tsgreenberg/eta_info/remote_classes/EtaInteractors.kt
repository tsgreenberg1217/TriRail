package com.tsgreenberg.eta_info.remote_classes

import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.ProgressBarState
import com.tsgreenberg.eta_info.toUIStopEta
import kotlinx.coroutines.flow.flow

data class EtaInteractors(
    val getEtaForStation: GetEtaForStation
) {
    companion object Factory {
        fun build(etaService: EtaService): EtaInteractors = EtaInteractors(
            getEtaForStation = GetEtaForStation(etaService)
        )
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
                DataState.Success(response.toUIStopEta())
            )
        } catch (e: Exception) {
            emit(
                DataState.Error(e.localizedMessage.orEmpty())
            )
        }finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }

    }
}

