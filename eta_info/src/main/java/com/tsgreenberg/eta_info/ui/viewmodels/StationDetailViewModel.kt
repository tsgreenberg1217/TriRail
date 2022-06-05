package com.tsgreenberg.eta_info.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsgreenberg.core.DataState
import com.tsgreenberg.eta_info.models.EtaInfoViewModelCache
import com.tsgreenberg.eta_info.models.TrainArrival
import com.tsgreenberg.eta_info.models.TrainInfoState
import com.tsgreenberg.eta_info.remote_classes.GetEtaForStation
import com.tsgreenberg.eta_info.remote_classes.GetTrainSchedulesForStation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StationDetailViewModel @Inject constructor(
    private val getEtaForStation: GetEtaForStation,
    private val cache: EtaInfoViewModelCache
) : ViewModel() {

    val state: MutableState<TrainInfoState> = mutableStateOf(TrainInfoState())

    init {
        getEstTrainArrivals(cache.stationId)
    }

    fun refresh() {
        getEstTrainArrivals(cache.stationId)
    }

    fun setTrainDirection(direction: String) {
        cache.trainDirection = direction
    }


    private fun getEstTrainArrivals(id: Int) {
        getEtaForStation.execute(id).onEach {
            when (it) {
                is DataState.Loading -> {
                    state.value = state.value.copy(
                        etaProgressBarState = it.progressBarState
                    )
                }

                is DataState.Success -> {
                    state.value = state.value.copy(arrivalMap = it.data)
                }

                is DataState.Error -> {
                    print(it.msg)
                }
            }
        }.launchIn(viewModelScope)
    }


}