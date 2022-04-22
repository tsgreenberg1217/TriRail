package com.tsgreenberg.eta_info.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.ProgressBarState
import com.tsgreenberg.eta_info.remote_classes.GetEtaForStation
import com.tsgreenberg.eta_info.UiStopEtaInfo
import com.tsgreenberg.eta_info.models.EtaStationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StationDetailViewModel @Inject constructor(
    private val getEtaForStation: GetEtaForStation
) : ViewModel() {

    val state: MutableState<EtaStationState> = mutableStateOf(EtaStationState())

    fun setStationEta(id: Int) {
        getEtaForStation.execute(id).onEach {
            when (it) {
                is DataState.Loading -> {
                    Log.d("WEAROS_TEST", "loading in vm is ${it.progressBarState}")
                    state.value = state.value.copy(
                        progressBarState = it.progressBarState
                    )
                }

                is DataState.Success -> {
                    Log.d("WEAROS_TEST", "sucess from vm")
                    state.value = state.value.copy(eta = it.data)
                }

                is DataState.Error -> {
                    Log.d("WEAROS_TEST", "error")
                    print(it.msg)
                }
            }
        }.launchIn(viewModelScope)
    }


}