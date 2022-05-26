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
import com.tsgreenberg.ui_components.TriRailChip
import com.tsgreenberg.ui_components.TriRailColors


@Composable
fun ChooseStationNavigator(
    triRailNav: TriRailNav,
    state: StationListState
) {
    val scalingLazyListState = rememberScalingLazyListState()
    ChooseStationList(
        state = state,
        scrollState = scalingLazyListState,
        triRailNav = triRailNav
    )
}

@Composable
internal fun ChooseStationList(
    state: StationListState,
    scrollState: ScalingLazyListState,
    triRailNav: TriRailNav,
) {
    TriRailScaffold(
        scrollState,
        progressBarState = state.progressBarState
    ) {
        state.stops?.let {
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
                items(it) { stop ->
                    TriRailChip(text = "${stop.name} Station", color = TriRailColors.Blue) {
                        triRailNav.navigate(
                            TriRailRootAction.StationInfo(stop.id, stop.shortName)
                        )
                    }
                }
            }
        }
    }


}

