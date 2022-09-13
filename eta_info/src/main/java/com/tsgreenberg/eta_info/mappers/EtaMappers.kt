package com.tsgreenberg.eta_info.mappers

import com.tsgreenberg.eta_info.models.*
import com.tsgreenberg.eta_info.models.General.NORTHERN_EOL
import com.tsgreenberg.eta_info.models.General.SOUTHERN_EOL
import com.tsgreenberg.eta_info.utils.InvalidDirectionException
import javax.inject.Inject

class EtaDtoMapper @Inject constructor() : Function1<GetStopEtaResponseDto, ArrivalData> {

    override fun invoke(response: GetStopEtaResponseDto): ArrivalData {

        return mutableListOf<EnRouteInfoDTO>().apply {
            response.etaDTOS.forEach { addAll(it.enRoute) }
        }.map {
            Arrival(
                info = it.minutes,
                trainId = it.scheduleNumber,
                status = it.status,
                statusColor = it.statusColor ?: "#ffffff",
                trackNumber = it.track,
                direction = it.direction
            )
        }.let {
            ArrivalData(
                stopId = response.etaDTOS.first().id.toInt(),
                data = it
            )
        }
    }
}

class TrainArrivalStateMapper @Inject constructor() :
    Function1<ArrivalData, Map<String, TrainArrival>> {
    override fun invoke(arrivalData: ArrivalData): Map<String, TrainArrival> {
        val (north, south) = splitToNorthAndSouth(arrivalData.data)

        val isNorthernEnd = arrivalData.stopId == NORTHERN_EOL
        val isSouthernEnd = arrivalData.stopId == SOUTHERN_EOL

        val northState = mapToSortedState(north, isNorthernEnd).first()
        val southState = mapToSortedState(south, isSouthernEnd).first()

        return mapOf(
            Keys.NORTH_KEY to northState,
            Keys.SOUTH_KEY to southState
        )

    }

    private fun isEndOfLine(arrivalData: ArrivalData, endId: Int) =
        arrivalData.run { stopId == endId }

    private fun splitToNorthAndSouth(data: List<Arrival>): Pair<List<Arrival>, List<Arrival>> {
        val north = mutableListOf<Arrival>()
        val south = mutableListOf<Arrival>()

        data.forEach {
            when (it.direction) {
                "North" -> north.add(it)
                "South" -> south.add(it)
                else -> throw InvalidDirectionException()
            }
        }

        return Pair(north, south)
    }

    private fun mapToSortedState(data: List<Arrival>, isEnd: Boolean): List<TrainArrival> {


        return when {
            isEnd -> listOf(TrainArrival.EndOfLine)

            data.isNotEmpty() -> data.sortedBy { it.info }.map {
                it.run {
                    TrainArrival.EstimatedArrival(
                        info,
                        trainId,
                        status,
                        statusColor,
                        trackNumber
                    )
                }
            }
            else -> listOf(TrainArrival.NoInformation)

        }
    }
}