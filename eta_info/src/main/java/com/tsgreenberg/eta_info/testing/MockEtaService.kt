package com.tsgreenberg.eta_info.testing

import com.tsgreenberg.eta_info.*
import com.tsgreenberg.eta_info.remote_classes.EtaService

class MockEtaService : EtaService {
    override suspend fun getVehicles(
        hasETAData: Int,
        isEtaOrdered: Int,
        isInService: Int,
        hasDirections: Int,
        token: String
    ): GetVehicleResponse {
        return GetVehicleResponse(listOf())
    }

    override suspend fun getStopEtas(stopId: Int, token: String): GetStopEtaResponse {
        return GetStopEtaResponse(
            listOf(
                StopEtaInfoDTO(
                    "1",
                    listOf(
                        EnRouteInfo(
                            0,
                            1,
                            18,
                            1,
                            20,
                            "11:45PM",
                            "11:45PM",
                            "On Time",
                            "P688",
                            "#39b139",
                            1,
                            "North",
                            "N.",
                            "1103",
                            1
                        ),
                        EnRouteInfo(
                            0,
                            1,
                            18,
                            1,
                            7,
                            "1:45PM",
                            "1:45PM",
                            "On Time",
                            "P677",
                            "#39b139",
                            10,
                            "South",
                            "S.",
                            "1103",
                            1
                        )
                    )
                )
            )
        )
    }
}