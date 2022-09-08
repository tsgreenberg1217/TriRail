package com.tsgreenberg.eta_info.android_test_utils

import com.tsgreenberg.eta_info.models.EnRouteInfoDTO
import com.tsgreenberg.eta_info.models.GetStopEtaResponseDto
import com.tsgreenberg.eta_info.models.StopEtaInfoDTO

object EtaMockData {
    val available = GetStopEtaResponseDto(
        listOf(
            StopEtaInfoDTO(
                "1",
                listOf(
                    EnRouteInfoDTO(
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
                    EnRouteInfoDTO(
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


    val notAvailable = GetStopEtaResponseDto(
        listOf(StopEtaInfoDTO("1", listOf()))
    )
}
