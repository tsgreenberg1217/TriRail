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
                        10,
                        "On Time",
                        "P618",
                        "#39b139",
                        1,
                        "North",
                        1
                    ),
                    EnRouteInfoDTO(
                        7,
                        "On Time",
                        "P620",
                        "#39b139",
                        1,
                        "North",
                        1
                    ),
                    EnRouteInfoDTO(
                        20,
                        "12:22PM",
                        "P609",
                        "#39b139",
                        1,
                        "South",
                        3
                    )
                )
            )
        )
    )


    val notAvailable = GetStopEtaResponseDto(
        listOf(StopEtaInfoDTO("1", listOf()))
    )


    val endOfLine = GetStopEtaResponseDto(
        listOf(
            StopEtaInfoDTO(
                "1",
                listOf(
                    EnRouteInfoDTO(
                        10,
                        "On Time",
                        "P618",
                        "#39b139",
                        1,
                        "North",
                        1
                    ),
                    EnRouteInfoDTO(
                        20,
                        "12:22PM",
                        "P609",
                        "#39b139",
                        1,
                        "South",
                        16
                    )
                )
            )
        )
    )
}
