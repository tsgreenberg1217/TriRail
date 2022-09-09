package com.tsgreenberg.eta_info.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsgreenberg.core.DataState
import com.tsgreenberg.eta_info.mappers.TrainArrivalStateMapper
import com.tsgreenberg.eta_info.models.EtaRefreshState
import com.tsgreenberg.eta_info.models.TrainInfoState
import com.tsgreenberg.eta_info.remote_classes.GetEtaForStation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainArrivalViewModel @Inject constructor(
    private val getEtaForStation: GetEtaForStation,
    private val mapper: TrainArrivalStateMapper
) : ViewModel() {

    val state: MutableState<TrainInfoState> = mutableStateOf(TrainInfoState())

    fun setRefreshState(refreshState: EtaRefreshState) {
        Log.d("TEST LOGS", "Refresh state set to $refreshState!")
        state.value = state.value.copy(
            etaRefreshState = refreshState
        )
    }

    fun setEnableTimer(seconds: Int) {
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

                is DataState.Success -> state.value.copy(
                    refreshId = id,
                    arrivalMap = mapper.invoke(it.data)
                )

                is DataState.Error -> state.value.copy(error = it.msg)

            }
        }.launchIn(viewModelScope)
    }

}