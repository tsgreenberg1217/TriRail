package com.tsgreenberg.station_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsgreenberg.core.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StationViewModel @Inject constructor(
    private val getStops: GetStops
) : ViewModel() {

    val state: MutableState<DataState<List<Stop>>> = mutableStateOf(DataState.Loading)

    init {
        getStops()
    }


    private fun getStops() {
        getStops.execute().onEach {
            state.value = it
        }.launchIn(viewModelScope)

    }
}