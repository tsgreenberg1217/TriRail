package com.tsgreenberg.eta_info.testing

import com.tsgreenberg.eta_info.GetStopEtaResponse
import com.tsgreenberg.eta_info.GetVehicleResponse
import com.tsgreenberg.eta_info.remote_classes.EtaService

class MockEtaService(
    private val response: GetStopEtaResponse
) : EtaService {
    override suspend fun getVehicles(
        hasETAData: Int,
        isEtaOrdered: Int,
        isInService: Int,
        hasDirections: Int,
        token: String
    ): GetVehicleResponse {
        return GetVehicleResponse(listOf())
    }

    override suspend fun getStopEtas(stopId: Int, token: String): GetStopEtaResponse = response

}