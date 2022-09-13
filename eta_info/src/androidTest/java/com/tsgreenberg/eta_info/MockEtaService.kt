package com.tsgreenberg.eta_info

import com.tsgreenberg.eta_info.models.GetStopEtaResponseDto
import com.tsgreenberg.eta_info.models.GetVehicleResponseDto
import com.tsgreenberg.eta_info.remote_classes.EtaService
import kotlinx.coroutines.delay

class MockEtaService(
    private val response: GetStopEtaResponseDto, private val delayed:Boolean = false
) : EtaService {
    override suspend fun getVehicles(
        hasETAData: Int,
        isEtaOrdered: Int,
        isInService: Int,
        hasDirections: Int,
        token: String
    ): GetVehicleResponseDto {
        return GetVehicleResponseDto(listOf())
    }

    override suspend fun getStopEtas(stopId: Int, token: String): GetStopEtaResponseDto {
        if(delayed) delay(2000)
        return response
    }

}