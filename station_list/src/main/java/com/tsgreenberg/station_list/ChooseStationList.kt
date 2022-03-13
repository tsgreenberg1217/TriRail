package com.tsgreenberg.station_list

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.navigation.TriRailNav
import com.tsgreenberg.ui_components.TriRailScaffold
import com.tsgreenberg.core.navigation.TriRailNavRouteAction
import com.tsgreenberg.core.navigation.TriRailRootAction


@Composable
fun ChooseStationNavigator(
    triRailNav: TriRailNav
) {
    val viewModel: StationViewModel = hiltViewModel()
    val scalingLazyListState = rememberScalingLazyListState()

    ChooseStationList(
        state = viewModel.state.value,
        scrollState = scalingLazyListState,
        triRailNav = triRailNav
    )
}

@Composable
internal fun ChooseStationList(
    state: DataState<List<Stop>>,
    scrollState: ScalingLazyListState,
    triRailNav: TriRailNav,
) {
    MaterialTheme {
        TriRailScaffold(scrollState) {
            when (state) {
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
                    ScalingLazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        state = scrollState
                    ) {
                        item {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Stations", textAlign = TextAlign.Center)
                            }
                        }
                        items(state.data) { stop ->
                            Chip(onClick = {
                                triRailNav.navigate(
                                    TriRailRootAction.StationInfo(stop.id)
                                )
                            },
                                colors = ChipDefaults.gradientBackgroundChipColors(
                                    startBackgroundColor = Color(0xFF4E90A6),
                                    endBackgroundColor = Color(0xFF132329)
                                ),
                                label = { Text(text = stop.name) }
                            )
                        }
                    }
                }

                is DataState.Error -> {
                    Text(text = state.msg)
                }

            }
        }
    }

}

