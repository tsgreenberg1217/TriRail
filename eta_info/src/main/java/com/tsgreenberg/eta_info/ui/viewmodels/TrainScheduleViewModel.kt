package com.tsgreenberg.eta_info.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsgreenberg.core.DataState
import com.tsgreenberg.eta_info.models.TrainScheduleState
import com.tsgreenberg.eta_info.models.UiTrainSchedule
import com.tsgreenberg.eta_info.remote_classes.GetTrainSchedulesForStation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TrainScheduleViewModel @Inject constructor(
    private val getTrainSchedulesForStation: GetTrainSchedulesForStation
) : ViewModel() {

    val state: MutableState<TrainScheduleState> = mutableStateOf(TrainScheduleState())

    fun getScheduleForStation(id: Int) {
        getTrainSchedulesForStation.execute(id).onEach {
            state.value = state.value.run {
                when (it) {
                    is DataState.Loading -> copy(progressBarState = it.progressBarState)

                    is DataState.Success -> {
                        val scheduleMap = buildMap<String, List<UiTrainSchedule>> {
                            val norths = mutableListOf<UiTrainSchedule>()
                            val souths = mutableListOf<UiTrainSchedule>()
                            it.data.forEach {
                                if (it.direction == "N") norths.add(it) else souths.add(it)
                            }
                            put("North", norths)
                            put("South", souths)
                        }
                        copy(trainSchedule = scheduleMap)
                    }

                    is DataState.Error -> copy(error = it.msg)
                }
            }
        }.launchIn(viewModelScope)
    }
}