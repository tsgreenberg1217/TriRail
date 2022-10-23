package com.tsgreenberg.fm_eta.mapperTests

import com.tsgreenberg.fm_eta.mappers.EtaDtoMapper
import com.tsgreenberg.fm_eta.models.*
import org.junit.Assert
import org.junit.Test

class EtaDtoMapperShould {

    // system under test
    private val mapper = EtaDtoMapper()

    @Test
    fun makeDataForNorthAndSouthTrains() {

        val dto = GetStopEtaResponseDto(
            etaDTOS = listOf(
                StopEtaInfoDTO(
                    id = "1",
                    enRoute = listOf(
                        EnRouteInfoDTO(
                            29,
                            "On Time",
                            "P618",
                            "#39b139",
                            1,
                            "North",
                            1
                        ),
                        EnRouteInfoDTO(
                            89,
                            "On Time",
                            "P620",
                            "#39b139",
                            1,
                            "North",
                            1
                        ),
                        EnRouteInfoDTO(
                            7,
                            "12:22PM",
                            "P609",
                            "#39b139",
                            2,
                            "South",
                            1
                        )
                    )
                )

            )
        )

        val expectedResult = listOf(
            ArrivalData(
                29,
                "P618",
                "On Time",
                "#39b139",
                1,
                1,
                "North"

            ),
            ArrivalData(
                89,
                "P620",
                "On Time",
                "#39b139",
                1,
                1,
                "North"
            ),
            ArrivalData(
                7,
                "P609",
                "12:22PM",
                "#39b139",
                2,
                1,
                "South"
            )
        )


        val mappedData = mapper.invoke(dto)

        Assert.assertEquals(mappedData, expectedResult)

    }


    @Test
    fun defaultToEmpytValuesWhenNoDataInResponse() {
        val dto = GetStopEtaResponseDto(
            etaDTOS = listOf(
                StopEtaInfoDTO(
                    id = "1",
                    enRoute = listOf()
                )

            )
        )
        Assert.assertEquals(
            mapper.invoke(dto),
            listOf<ArrivalData>()
        )
    }

}