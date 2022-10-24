package com.tsgreenberg.fm_schedule.mappers

import com.tsgreenberg.fm_schedule.models.TrainSchedule

class TrainScheduleStateMapper : Function1<List<TrainSchedule>, Map<String, List<TrainSchedule>>> {
    override operator fun invoke(schedules: List<TrainSchedule>): Map<String, List<TrainSchedule>> =
        buildMap {
            val norths = mutableListOf<TrainSchedule>()
            val souths = mutableListOf<TrainSchedule>()
            schedules.forEach {
                if (it.direction == "N") norths.add(it) else souths.add(it)
            }
            put("North", norths)
            put("South", souths)
        }

}