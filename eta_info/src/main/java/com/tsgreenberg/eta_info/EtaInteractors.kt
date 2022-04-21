package com.tsgreenberg.eta_info

import com.tsgreenberg.core.DataState
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
            val response = etaService.getStopEtas(id)
//            Log.d("TRI RAIL", "data from flow is $response")
            emit(
                DataState.Success(response.toUIStopEta())
            )
        } catch (e: Exception) {
            emit(
                DataState.Error(e.localizedMessage.orEmpty())
            )
        }

    }
}

