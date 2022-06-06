package com.tsgreenberg.eta_info.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.tsgreenberg.eta_info.R
import com.tsgreenberg.eta_info.models.EnRouteInfo
import com.tsgreenberg.eta_info.models.TrainArrival
import com.tsgreenberg.eta_info.models.TrainInfoState
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_TITLE_NORTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_TITLE_SOUTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_VIEWPAGER
import com.tsgreenberg.eta_info.ui.components.TrackArrow
import com.tsgreenberg.ui_components.*


@OptIn(ExperimentalPagerApi::class)
@Composable
fun EtaScreen(
    shortName: String = "",
    state: TrainInfoState,
    refresh: () -> Unit,
    goToTrainSchedule: (String) -> Unit
) {
    val pagerState = rememberPagerState()
    val scalingLazyListState = rememberScalingLazyListState()
    TriRailScaffold(
        extraText = shortName,
        progressBarState = state.etaProgressBarState,
        error = state.error,
        scalingLazyListState = scalingLazyListState,
        isVisible = !pagerState.isScrollInProgress
    ) {


        pagerState.isScrollInProgress
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
                    state.arrivalMap?.let {
                        UpcomingArrivalsSection(it) { refresh() }
                    }
                } else {
                    ShowScheduleScreen { d -> goToTrainSchedule(d) }
                }
            }
            ViewPagerScroll(pagerState = pagerState)

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

        TriRailButton(
            Modifier.fillMaxWidth(),
            onClick = { onClick("N") }
        ) {
            Text(text = "Northbound Schedule", textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        TriRailButton(
            Modifier.fillMaxWidth(),
            onClick = { onClick("S") }
        ) {
            Text("Southbound Schedule", textAlign = TextAlign.Center)
        }
    }
}

fun Map<String, EnRouteInfo>.getSouth(): EnRouteInfo? = get("South")
fun Map<String, EnRouteInfo>.getNorth(): EnRouteInfo? = get("North")

@Composable
fun UpcomingArrivalsSection(
    enRouteMap: Map<String, TrainArrival>,
    onRefresh: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Column(
            Modifier.weight(4f, true),
            verticalArrangement = Arrangement.Center,
        ) {
            val northTrains = enRouteMap["North"]
            val southTrains = enRouteMap["South"]
            ShowRouteInfo(
                direction = "North",
                arrival = northTrains,
            )

            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            Box(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .height(1.dp)
                    .width(40.dp)
                    .background(color = Color.White)

            )
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            ShowRouteInfo(
                direction = "South",
                arrival = southTrains,
            )
        }
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f, true),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
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
fun ShowRouteInfo(
    direction: String,
    arrival: TrainArrival?,
) {
    arrival?.let {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .testTag(if (direction == "North") ETA_TITLE_NORTH else ETA_TITLE_SOUTH),
                    text = "${direction}bound",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp
                    )
                )
                if (it is TrainArrival.EstimatedArrival) {
                    it.status?.let { status ->
                        Text(
                            text = " $status",

                            style = TextStyle(
                                color = Color(
                                    it.statusColor?.let { android.graphics.Color.parseColor(it) }
                                        ?: 0xFFF
                                ),
                                fontSize = 12.sp
                            )
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.padding(vertical = 2.dp))

            when (it) {
                is TrainArrival.EstimatedArrival -> {
                    RouteInfo(it.info.toEtaString(), it.trackNumber.toString(), it.trainId)
                }

                is TrainArrival.NoInformation -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .testTag(if (direction == "North") ETA_TITLE_NORTH else ETA_TITLE_SOUTH),
                            text = "No train eta at this time...",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                    }
                }
            }
        }
    }

}

@Composable
fun RouteInfo(info: String, trackNumber: String? = null, trainId: String) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {

            TrackArrow(
                fontSize = 10.sp,
                trackTxt = trainId
            )
            trackNumber?.let {
                Text(
                    text = "Track #$it",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp
                    )
                )
            }

        }
        Row(
            verticalAlignment = Bottom,
        ) {
            Text(
                text = info,
                style = TextStyle(
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