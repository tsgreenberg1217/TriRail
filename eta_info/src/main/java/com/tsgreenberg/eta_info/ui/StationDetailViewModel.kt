package com.tsgreenberg.eta_info.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsgreenberg.core.DataState
import com.tsgreenberg.eta_info.models.EtaInfoViewModelCache
import com.tsgreenberg.eta_info.models.TrainInfoState
import com.tsgreenberg.eta_info.remote_classes.GetEtaForStation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StationDetailViewModel @Inject constructor(
    private val getEtaForStation: GetEtaForStation,
    cache: EtaInfoViewModelCache
) : ViewModel() {

    val state: MutableState<TrainInfoState> = mutableStateOf(TrainInfoState())

    init {
        setStationEta(cache.stationId)
    }

    fun refresh(id: Int) {
        setStationEta(id)
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
                    state.value = state.value.copy(eta = it.data)
                }

                is DataState.Error -> {
                    print(it.msg)
                }
            }
        }.launchIn(viewModelScope)
    }


}