package com.tsgreenberg.eta_info.ui.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsgreenberg.core.DataState
import com.tsgreenberg.eta_info.models.EtaInfoViewModelCache
import com.tsgreenberg.eta_info.models.EtaRefreshState
import com.tsgreenberg.eta_info.models.TrainArrival
import com.tsgreenberg.eta_info.models.TrainInfoState
import com.tsgreenberg.eta_info.remote_classes.GetEtaForStation
import com.tsgreenberg.eta_info.remote_classes.GetTrainSchedulesForStation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TrainArrivalViewModel @Inject constructor(
    private val getEtaForStation: GetEtaForStation,
    private val cache: EtaInfoViewModelCache
) : ViewModel() {

    val state: MutableState<TrainInfoState> = mutableStateOf(TrainInfoState())
    var job:Job? = null // for testing purposes only

    init {
        getEstTrainArrivals(cache.stationId)
    }

    fun refresh() {
        getEstTrainArrivals(cache.stationId)
    }

    fun setRefreshState(refreshState:EtaRefreshState){
        Log.d("TEST LOGS", "Refresh state set to $refreshState!")
        state.value = state.value.copy(
            etaRefreshState = refreshState
        )
    }

    fun setEnableTimer(seconds:Int){
        job = viewModelScope.launch(Dispatchers.Main) {
            try {
                Log.d("TEST LOGS", "CR launched!")
                delay(1000L * seconds)
                setRefreshState(EtaRefreshState.Enabled)
            }finally {
                Log.d("TEST LOGS", "CR canceled")
            }

        }
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
                    val arrivalMap = it.data.toMutableMap()

                    when (id) {
                        1 -> arrivalMap["South"] = TrainArrival.EndOfLine
                        18 -> arrivalMap["North"] = TrainArrival.EndOfLine
                    }

                    state.value = state.value.copy(arrivalMap = arrivalMap)
                }

                is DataState.Error -> {
                    state.value = state.value.copy(error = it.msg)
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TEST LOGS", "VM cleared")
    }
}