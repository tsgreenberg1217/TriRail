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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
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
        if (state.value.etaRefreshState is EtaRefreshState.Enabled) {
            getEstTrainArrivals(cache.stationId)
            Log.d("Timer test", "staring timer")
            viewModelScope.launch(Dispatchers.Main) {
                state.value = state.value.copy(
                    etaRefreshState = EtaRefreshState.Disabled(
                        Calendar.getInstance().timeInMillis
                    )
                )
                delay(1000 * 60)
                state.value = state.value.copy(
                    etaRefreshState = EtaRefreshState.Enabled
                )
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


}