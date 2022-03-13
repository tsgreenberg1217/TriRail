package com.tsgreenberg.station_info

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.navigation.TriRailNav
import com.tsgreenberg.ui_components.TriRailScaffold

@Composable
fun EtaScreen(
    triRailNav: TriRailNav, id: Int
) {
    val viewModel: StationDetailViewModel = hiltViewModel()
    viewModel.setStationEta(id)
    val scalingLazyListState = rememberScalingLazyListState()
    TriRailScaffold(scalingLazyListState = scalingLazyListState) {
        when (val state = viewModel.state.value) {
            is DataState.Loading -> {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        Modifier.size(30.dp)
                    )
                }
            }
            is DataState.Success -> {
                val time = state.data[0].enRoute[0].time
                Log.d("TRI RAIL", "info from ui is $time")
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = time, style = TextStyle(
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}