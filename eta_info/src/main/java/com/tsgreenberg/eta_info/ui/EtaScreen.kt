package com.tsgreenberg.eta_info.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.tsgreenberg.core.navigation.TriRailNav
import com.tsgreenberg.eta_info.EnRouteInfo
import com.tsgreenberg.eta_info.R
import com.tsgreenberg.eta_info.models.EtaStationState
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_TITLE_NORTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_TITLE_SOUTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_VIEWPAGER
import com.tsgreenberg.ui_components.TriRailScaffold
import com.tsgreenberg.ui_components.ViewPagerScroll

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EtaScreen(
    triRailNav: TriRailNav,
    state: EtaStationState
) {
    val scalingLazyListState = rememberScalingLazyListState()
    TriRailScaffold(
        scalingLazyListState = scalingLazyListState,
        state.progressBarState
    ) {

        state.eta?.let {
            val enRouteMap = it.etaMap
            val pagerState = rememberPagerState()

            Box(
                modifier = Modifier
                    .fillMaxSize().padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                ViewPagerScroll(pagerState = pagerState)
                VerticalPager(
                    2,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(ETA_VIEWPAGER)
                ) { page ->
                    if (page == 0) {
                        UpcomingArrivalsSection(enRouteMap)
                    } else {
                        Text(text = "Working on it...")
                    }
                }

            }
        }

    }
}

fun Map<String, List<EnRouteInfo>>.getSouth(): List<EnRouteInfo>? = get("South")
fun Map<String, List<EnRouteInfo>>.getNorth(): List<EnRouteInfo>? = get("North")

@Composable
fun UpcomingArrivalsSection(enRouteMap: Map<String, List<EnRouteInfo>>) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            val northTrains = enRouteMap.getNorth()
            val southTrains = enRouteMap.getSouth()
            ShowRouteInfo(direction = "North", list = northTrains)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            ShowRouteInfo(direction = "South", list = southTrains)
        }
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RefreshButton {

            }

        }
    }
}


@Composable
fun RefreshButton(onClick: () -> Unit) {
    Button(
        onClick = onClick, modifier = Modifier.height(40.dp), colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_sync_48px),
                contentDescription = "Refresh ETAs",
                Modifier.size(24.dp)
            )
        }
    }
}


@Composable
fun ShowRouteInfo(direction: String, list: List<EnRouteInfo>?) {
    if (list.isNullOrEmpty()) {
        Text(text = "No ${direction}bound trains at this time")
    } else {
        RouteInfo(direction, list.first())
    }
}

@Composable
fun RouteInfo(direction: String, info: EnRouteInfo) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                modifier = Modifier
                    .testTag(if (direction == "North") ETA_TITLE_NORTH else ETA_TITLE_SOUTH),
                text = "${direction}bound",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp
                )
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 2.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {

                TrackArrow(
                    modifier = Modifier.height(24.dp),
                    trackTxt = info.scheduleNumber
                )

                Text(
                    text = "Track #${info.track}",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp
                    )
                )
            }
            Row {
                Text(
                    text = "${info.minutes}", style = TextStyle(
                        color = Color.White,
                        fontSize = 24.sp
                    ),
                    textAlign = TextAlign.Center
                )
                Text(

                    modifier = Modifier
                        .testTag(direction),
                    text = "mins", style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}