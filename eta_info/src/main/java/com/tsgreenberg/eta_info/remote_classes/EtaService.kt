package com.tsgreenberg.eta_info.remote_classes

import com.tsgreenberg.eta_info.models.GetStopEtaResponseDto
import com.tsgreenberg.eta_info.models.GetVehicleResponseDto

interface EtaService {
    suspend fun getVehicles(
        hasETAData: Int = 1,
        isEtaOrdered: Int = 1,
        isInService: Int = 0,
        hasDirections: Int = 1,
        token: String = "TESTING"
    ): GetVehicleResponseDto

    suspend fun getStopEtas(
        stopId: Int,
        token: String = "TESTING"
    ): GetStopEtaResponseDto
}