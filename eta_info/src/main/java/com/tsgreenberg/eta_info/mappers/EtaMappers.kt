package com.tsgreenberg.eta_info.mappers

import com.tsgreenberg.eta_info.models.*
import com.tsgreenberg.eta_info.models.General.NORTHERN_EOL
import com.tsgreenberg.eta_info.models.General.SOUTHERN_EOL
import com.tsgreenberg.eta_info.utils.InvalidDirectionException
import javax.inject.Inject

class EtaDtoMapper @Inject constructor(): Function1<GetStopEtaResponseDto, List<ArrivalData>> {

    override fun invoke(response: GetStopEtaResponseDto): List<ArrivalData> {

        return mutableListOf<EnRouteInfoDTO>().apply {
            response.etaDTOS.forEach { addAll(it.enRoute) }
        }.map {
            ArrivalData(
                info = it.minutes,
                trainId = it.scheduleNumber,
                status = it.status,
                statusColor = it.statusColor ?: "#FFF",
                trackNumber = it.track,
                stopId = it.stopId,
                direction = it.direction
            )
        }
    }
}

class TrainArrivalStateMapper @Inject constructor() : Function1<List<ArrivalData>, Map<String, TrainArrival>> {
    override fun invoke(data: List<ArrivalData>): Map<String, TrainArrival> {
        val (north, south) = splitToNorthAndSouth(data)
        val northState = mapToSortedState(north)
        val southState = mapToSortedState(south)
        return mapOf(
            Keys.NORTH_KEY to northState.first(),
            Keys.SOUTH_KEY to southState.first()
        )

    }

    private fun splitToNorthAndSouth(data: List<ArrivalData>): Pair<List<ArrivalData>, List<ArrivalData>> {
        val north = mutableListOf<ArrivalData>()
        val south = mutableListOf<ArrivalData>()

        data.forEach {
            when (it.direction) {
                "North" -> north.add(it)
                "South" -> south.add(it)
                else -> throw InvalidDirectionException()
            }
        }

        return Pair(north, south)
    }

    private fun mapToSortedState(data: List<ArrivalData>): List<TrainArrival> {
        return if (data.isEmpty()) {
            listOf(TrainArrival.NoInformation)
        } else {
            data.sortedBy { it.info }.map {
                when (it.stopId) {
                    SOUTHERN_EOL, NORTHERN_EOL -> TrainArrival.EndOfLine
                    else -> it.run {
                        TrainArrival.EstimatedArrival(
                            info,
                            trainId,
                            status,
                            statusColor,
                            trackNumber
                        )
                    }

                }
            }
        }
    }
}