package com.tsgreenberg.eta_info.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsgreenberg.core.DataState
import com.tsgreenberg.eta_info.models.EtaInfoViewModelCache
import com.tsgreenberg.eta_info.models.TrainScheduleState
import com.tsgreenberg.eta_info.remote_classes.GetTrainSchedulesForStation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TrainScheduleViewModel @Inject constructor(
    private val getTrainSchedulesForStation: GetTrainSchedulesForStation,
    private val cache: EtaInfoViewModelCache
) : ViewModel() {
    val state: MutableState<TrainScheduleState> = mutableStateOf(TrainScheduleState())

    init {
        getScheduleForStation(
            cache.stationId,
            cache.trainDirection
        )
    }

    private fun getScheduleForStation(id: Int, direction: String) {
        getTrainSchedulesForStation.execute(id, direction).onEach {
            state.value = when (it) {
                is DataState.Loading -> {
                    state.value.copy(progressBarState = it.progressBarState)
                }
                is DataState.Success -> {
                    state.value.copy(trainSchedule = it.data)
                }

                is DataState.Error -> {
                    state.value.copy(error = it.msg)
                }
            }
        }.launchIn(viewModelScope)
    }
}