package com.tsgreenberg.station_info

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.navigation.NavConstants.STATION_ID
import com.tsgreenberg.core.navigation.TriRailNav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StationDetailViewModel @Inject constructor(
    private val getEtaForStation: GetEtaForStation
) : ViewModel() {

    val state: MutableState<DataState<List<StopEtaInfo>>> = mutableStateOf(DataState.Loading)

    fun setStationEta(id: Int) {
        getEtaForStation.execute(id).onEach {
            state.value = it
        }.launchIn(viewModelScope)
    }


}