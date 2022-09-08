package com.tsgreenberg.eta_info.mapperTests

import com.tsgreenberg.eta_info.mappers.TrainArrivalStateMapper
import com.tsgreenberg.eta_info.models.ArrivalData
import com.tsgreenberg.eta_info.models.Keys
import com.tsgreenberg.eta_info.models.TrainArrival
import com.tsgreenberg.eta_info.utils.InvalidDirectionException
import org.junit.Assert
import org.junit.Test

class TrainArrivalStateMapperShould {

    // system under test
    private val mapper = TrainArrivalStateMapper()

    @Test
    fun containsCorrectKeysInMap(){
        val data = listOf(
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
                7,
                "P609",
                "12:22PM",
                "#39b139",
                2,
                16,
                "South"
            )
        )

        mapper.invoke(data).run {
            assert(containsKey("North_Key"))
            assert(containsKey("South_Key"))
        }


    }

    @Test
    fun mapAvailNorthAndSouth() {
        val data = listOf(
            ArrivalData(
                40,
                "P618",
                "On Time",
                "#39b139",
                1,
                2,
                "North"

            ),
            ArrivalData(
                29,
                "P615",
                "On Time",
                "#39b139",
                1,
                2,
                "North"

            ),
            ArrivalData(
                20,
                "P609",
                "12:22PM",
                "#39b139",
                2,
                15,
                "South"
            ),
            ArrivalData(
                7,
                "P609",
                "12:22PM",
                "#39b139",
                2,
                15,
                "South"
            )
        )

        val state = mapper.invoke(data)

        val expectedNorth = TrainArrival.EstimatedArrival(
            29,
            "P615",
            "On Time",
            "#39b139",
            1
        )



        val expectedSouth = TrainArrival.EstimatedArrival(
            7,
            "P609",
            "12:22PM",
            "#39b139",
            2
        )

        Assert.assertEquals(
            state[Keys.NORTH_KEY], expectedNorth
        )

        Assert.assertEquals(
            state[Keys.SOUTH_KEY], expectedSouth
        )

    }

    @Test
    fun mapEndOfLineData() {

        val data = listOf(
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
                7,
                "P609",
                "12:22PM",
                "#39b139",
                2,
                16,
                "South"
            )
        )

        val state = mapper.invoke(data)

        val expectedNorth = TrainArrival.EndOfLine
        val expectedSouth = TrainArrival.EndOfLine

        Assert.assertEquals(
            state[Keys.NORTH_KEY], expectedNorth
        )

        Assert.assertEquals(
            state[Keys.SOUTH_KEY], expectedSouth
        )

    }

    @Test
    fun mapEmptyData() {

        val data = listOf<ArrivalData>()
        val state = mapper.invoke(data)

        val expectedNorth = TrainArrival.NoInformation
        val expectedSouth = TrainArrival.NoInformation

        Assert.assertEquals(
            state[Keys.NORTH_KEY], expectedNorth
        )

        Assert.assertEquals(
            state[Keys.SOUTH_KEY], expectedSouth
        )

    }

    @Test(expected = InvalidDirectionException::class)
    fun throwWhenInvalidDirection() {

        val data = listOf(
            ArrivalData(
                29,
                "P618",
                "On Time",
                "#39b139",
                1,
                1,
                "SSouth"

            ),
        )

        val state = mapper.invoke(data)
    }
}
