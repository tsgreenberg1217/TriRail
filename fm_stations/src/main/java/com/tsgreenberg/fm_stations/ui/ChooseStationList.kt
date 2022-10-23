package com.tsgreenberg.fm_stations.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.tsgreenberg.core.navigation.TriRailNav
import com.tsgreenberg.fm_stations.models.StationListState
import com.tsgreenberg.fm_stations.navigation.StationListNavigation
import com.tsgreenberg.ui_components.TriRailScaffold
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
        progressBarState = state.progressBarState,
        error = state.error
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
                    TriRailChip(
                        Modifier.fillMaxWidth(),
                        text = "${stop.name} Station",
                        color = TriRailColors.Blue
                    ) {
                        triRailNav.navigate(
                            StationListNavigation.StationEtaInfo(stop.id, stop.shortName)
                        )
                    }
                }
            }
        }
    }


}

