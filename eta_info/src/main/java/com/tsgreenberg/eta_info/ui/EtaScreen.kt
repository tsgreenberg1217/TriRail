package com.tsgreenberg.eta_info.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
import com.tsgreenberg.eta_info.EnRouteInfo
import com.tsgreenberg.eta_info.R
import com.tsgreenberg.eta_info.models.TrainInfoState
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_TITLE_NORTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_TITLE_SOUTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_VIEWPAGER
import com.tsgreenberg.ui_components.TriRailButton
import com.tsgreenberg.ui_components.TriRailScaffold
import com.tsgreenberg.ui_components.ViewPagerScroll

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EtaScreen(
    state: TrainInfoState,
    refresh: () -> Unit,
    goToTrainSchedule: (String) -> Unit
) {
    val scalingLazyListState = rememberScalingLazyListState()
    TriRailScaffold(
        progressBarState = state.etaProgressBarState,
        scalingLazyListState = scalingLazyListState,
    ) {

        state.eta?.let {
            val enRouteMap = it.etaMap
            val pagerState = rememberPagerState()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                VerticalPager(
                    2,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(ETA_VIEWPAGER)
                ) { page ->
                    if (page == 0) {
                        UpcomingArrivalsSection(enRouteMap) {
                            refresh()
                        }
                    } else {
                        ShowScheduleScreen { d -> goToTrainSchedule(d) }
                    }
                }
                ViewPagerScroll(pagerState = pagerState)

            }
        }

    }
}

@Composable
fun ShowScheduleScreen(onClick: (String) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(all = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TriRailButton(text = "NorthBound Schedule") {
            onClick("N")
        }
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        TriRailButton(text = "SouthBound Schedule") {
            onClick("S")
        }
    }
}

fun Map<String, List<EnRouteInfo>>.getSouth(): List<EnRouteInfo>? = get("South")
fun Map<String, List<EnRouteInfo>>.getNorth(): List<EnRouteInfo>? = get("North")

@Composable
fun UpcomingArrivalsSection(enRouteMap: Map<String, List<EnRouteInfo>>, onRefresh: () -> Unit) {
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

            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            Box(
                modifier = Modifier
                    .padding(horizontal = 80.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Color.White)

            )
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            ShowRouteInfo(direction = "South", list = southTrains)
        }
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RefreshButton { onRefresh() }

        }
    }
}


@Composable
fun RefreshButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(40.dp)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .testTag(if (direction == "North") ETA_TITLE_NORTH else ETA_TITLE_SOUTH),
                    text = "No information",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
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
                        fontSize = 12.sp
                    )
                )
            }
            Row(
                verticalAlignment = Bottom,
            ) {
                Text(
                    text = "${info.minutes} min.", style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        lineHeight = 0.sp,
                        letterSpacing = 0.sp
                    ),
                    textAlign = TextAlign.End
                )
            }

        }
    }
}