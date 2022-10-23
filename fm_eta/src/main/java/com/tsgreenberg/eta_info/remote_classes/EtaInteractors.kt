package com.tsgreenberg.fm_eta.remote_classes

import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.ProgressBarState
import com.tsgreenberg.fm_eta.mappers.EtaDtoMapper
import kotlinx.coroutines.flow.flow

data class EtaInteractors(
    val getEtaForStation: GetEtaForStation,
) {
    companion object Factory {
        fun build(
            etaService: EtaService,
            etaMapper: EtaDtoMapper
        ): EtaInteractors = EtaInteractors(
            getEtaForStation = GetEtaForStation(etaService, etaMapper),
        )
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