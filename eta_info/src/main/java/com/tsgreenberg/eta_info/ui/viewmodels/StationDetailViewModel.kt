package com.tsgreenberg.eta_info.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.navigation.TriRailRootAction
import com.tsgreenberg.eta_info.UiTrainSchedule
import com.tsgreenberg.eta_info.models.EtaInfoViewModelCache
import com.tsgreenberg.eta_info.models.TrainInfoState
import com.tsgreenberg.eta_info.remote_classes.GetEtaForStation
import com.tsgreenberg.eta_info.remote_classes.GetTrainSchedulesForStation
import com.tsgreenberg.etainfo.GetScheduleForStation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StationDetailViewModel @Inject constructor(
    private val getEtaForStation: GetEtaForStation,
    private val getTrainSchedulesForStation: GetTrainSchedulesForStation,
    private val cache: EtaInfoViewModelCache
) : ViewModel() {


    val state: MutableState<TrainInfoState> = mutableStateOf(TrainInfoState())

    init {
        setStationEta(cache.stationId)
    }

    fun refresh() {
        setStationEta(cache.stationId)
    }

    fun setTrainDirection(direction: String) {
        cache.trainDirection = direction
    }

    private fun getExpectedTimes(direction: String) {
        getTrainSchedulesForStation.execute(cache.stationId, direction[0].toString()).onEach {
            when (it) {
                is DataState.Loading -> {
                    state.value = state.value.copy(
                        etaProgressBarState = it.progressBarState
                    )
                }

                is DataState.Success -> {
                    val newMap = mutableMapOf<String, UiTrainSchedule?>().apply {
                        put(direction, if (it.data.isEmpty()) null else it.data.first())
                    }
                    state.value.expectedTimes?.run {
                        forEach { (t, u) -> newMap[t] = u }
                    }

                    state.value = state.value.copy(expectedTimes = newMap)
                }

                is DataState.Error -> {
                    print(it.msg)
                }
            }
        }.launchIn(viewModelScope)

    }

    private fun setStationEta(id: Int) {
        getEtaForStation.execute(id).onEach {
            when (it) {
                is DataState.Loading -> {
                    state.value = state.value.copy(
                        etaProgressBarState = it.progressBarState
                    )
                }

                is DataState.Success -> {
                    it.data.etaMap.forEach { (k, _) ->
                        if (it.data.etaMap[k] == null) getExpectedTimes(k)
                    }
                    state.value = state.value.copy(eta = it.data)
                }

                is DataState.Error -> {
                    print(it.msg)
                }
            }
        }.launchIn(viewModelScope)
    }


}