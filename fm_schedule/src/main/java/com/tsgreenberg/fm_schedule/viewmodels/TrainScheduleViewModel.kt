package com.tsgreenberg.fm_schedule.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.navigation.NavConstants
import com.tsgreenberg.fm_schedule.mappers.TrainScheduleStateMapper
import com.tsgreenberg.fm_schedule.use_cases.GetTrainSchedulesForStation
import com.tsgreenberg.fm_schedule.models.TrainScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TrainScheduleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTrainSchedulesForStation: GetTrainSchedulesForStation,
    private val stateMapper: TrainScheduleStateMapper
) : ViewModel() {

    val state: MutableState<TrainScheduleState> = mutableStateOf(TrainScheduleState())


    init {
        val id = savedStateHandle.get<Int>(NavConstants.STATION_ID) ?: -1
        getScheduleForStation(id)
    }

    fun getScheduleForStation(id: Int) {
        getTrainSchedulesForStation.execute(id).onEach {
            state.value = state.value.run {
                when (it) {
                    is DataState.Loading -> copy(progressBarState = it.progressBarState)

                    is DataState.Success -> copy(trainSchedule = stateMapper(it.data))

                    is DataState.Error -> copy(error = it.msg)
                }
            }
        }.launchIn(viewModelScope)
    }
}