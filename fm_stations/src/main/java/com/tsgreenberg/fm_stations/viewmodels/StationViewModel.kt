package com.tsgreenberg.fm_stations.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsgreenberg.core.DataState
import com.tsgreenberg.fm_stations.use_cases.GetStops
import com.tsgreenberg.fm_stations.models.StationListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StationViewModel @Inject constructor(
    private val getStops: GetStops
) : ViewModel() {

    val state: MutableState<StationListState> = mutableStateOf(StationListState())

    init {
        getStops()
    }


    private fun getStops() {
        getStops.execute().onEach {
            when(it){
                is DataState.Loading ->{
                    state.value = state.value.copy(progressBarState = it.progressBarState)
                }

                is DataState.Success ->{
                    state.value = state.value.copy(stops = it.data)
                }

                is DataState.Error ->{
                    state.value = state.value.copy(error = it.msg)
                }
            }
        }.launchIn(viewModelScope)

    }
}