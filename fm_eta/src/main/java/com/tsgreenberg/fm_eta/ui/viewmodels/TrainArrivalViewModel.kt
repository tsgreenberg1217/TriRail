package com.tsgreenberg.fm_eta.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.navigation.NavConstants
import com.tsgreenberg.eta_info.mappers.TrainArrivalStateMapper
import com.tsgreenberg.fm_eta.models.EtaRefreshState
import com.tsgreenberg.fm_eta.models.TrainInfoState
import com.tsgreenberg.fm_eta.remote_classes.GetEtaForStation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainArrivalViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getEtaForStation: GetEtaForStation,
    private val mapper: TrainArrivalStateMapper
) : ViewModel() {

    private val refreshTime = 60

    val state: MutableState<TrainInfoState> = mutableStateOf(TrainInfoState())

    init {
        val id = savedStateHandle.get<Int>(NavConstants.STATION_ID) ?: -1
        getEstTrainArrivals(id)
    }


    fun initialRefreshRequest(id: Int, timeRequested: Long) {
        if (state.value.etaRefreshState is EtaRefreshState.Enabled) {
            getEstTrainArrivals(id)
            setRefreshState(
                EtaRefreshState.Disabled(timeRequested)
            )
            setEnableTimer(refreshTime)
        }
    }


    private fun setRefreshState(refreshState: EtaRefreshState) {
        Log.d("TEST LOGS", "Refresh state set to $refreshState!")
        state.value = state.value.copy(
            etaRefreshState = refreshState
        )
    }

    private fun setEnableTimer(seconds: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                Log.d("TEST LOGS", "CR launched!")
                delay(1000L * seconds)
                setRefreshState(EtaRefreshState.Enabled)
            } finally {
                Log.d("TEST LOGS", "CR canceled")
            }

        }
    }

    fun getEstTrainArrivals(id: Int) {
        getEtaForStation.execute(id).onEach {
            state.value = when (it) {
                is DataState.Loading -> state.value.copy(
                    etaProgressBarState = it.progressBarState
                )

                is DataState.Success -> {
                    val map = mapper.invoke(it.data)
                    state.value.copy(
                        refreshId = id,
                        arrivalMap = map
                    )
                }

                is DataState.Error -> state.value.copy(error = it.msg)

            }
        }.launchIn(viewModelScope)
    }

}