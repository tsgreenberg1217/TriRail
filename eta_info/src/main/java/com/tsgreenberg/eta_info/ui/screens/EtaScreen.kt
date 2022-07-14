package com.tsgreenberg.eta_info.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.tsgreenberg.eta_info.R
import com.tsgreenberg.eta_info.models.EtaRefreshState
import com.tsgreenberg.eta_info.models.TrainArrival
import com.tsgreenberg.eta_info.models.TrainInfoState
import com.tsgreenberg.eta_info.testing.TestingTags
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_VIEWPAGER
import com.tsgreenberg.eta_info.ui.components.TrackArrow
import com.tsgreenberg.ui_components.*
import java.util.*


@OptIn(ExperimentalPagerApi::class)
@Composable
fun EtaScreen(
    shortName: String = "",
    state: TrainInfoState,
    refresh: () -> Unit,
    goToTrainSchedule: (String) -> Unit,
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
                        UpcomingArrivalsSection(
                            it,
                            state.etaRefreshState,
                            onRefresh = refresh,
                            goToTrainSchedule = goToTrainSchedule,
                        )
                    }
                } else {
                    ShowScheduleScreen(goToSchedule = goToTrainSchedule)
                }
            }
            ViewPagerScroll(pagerState = pagerState)

        }


    }
}

const val NB = "Northbound Schedule"
const val SB = "Southbound Schedule"
const val N = "N"
const val S = "S"

@Composable
fun ShowScheduleScreen(
    goToSchedule: (String) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(all = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TriRailButton(
            Modifier
                .fillMaxWidth()
                .testTag(TestingTags.NORTH_SCHEDULE_BUTTON),
            color = TriRailColors.Green,
            onClick = {
                goToSchedule(N)
            }
        ) {
            Text(text = NB, textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        TriRailButton(
            Modifier
                .fillMaxWidth()
                .testTag(TestingTags.SOUTH_SCHEDULE_BUTTON),
            color = TriRailColors.Orange,
            onClick = {
                goToSchedule(S)
            }
        ) {
            Text(SB, textAlign = TextAlign.Center)
        }
    }
}

const val SOUTHBOUND_ETA = "Southbound ETA"
const val NORTHBOUND_ETA = "Northbound ETA"
const val NORTH = "North"
const val SOUTH = "South"

@Composable
fun UpcomingArrivalsSection(
    enRouteMap: Map<String, TrainArrival>,
    etaRefreshState: EtaRefreshState,
    onRefresh: () -> Unit,
    goToTrainSchedule: (String) -> Unit
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
            val northTrains = enRouteMap[NORTH]
            val southTrains = enRouteMap[SOUTH]
            ShowRouteInfo(
                title = NORTHBOUND_ETA,
                arrival = northTrains,
                goToTrainSchedule = goToTrainSchedule
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
                title = SOUTHBOUND_ETA,
                arrival = southTrains,
                goToTrainSchedule = goToTrainSchedule
            )
        }
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f, true),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            RefreshButton(etaRefreshState, onRefresh = onRefresh)
        }
    }
}


@Composable
fun RefreshButton(etaRefreshState: EtaRefreshState, onRefresh: () -> Unit) {
    val isEnabled = etaRefreshState is EtaRefreshState.Enabled
    val ctx = LocalContext.current
    TriRailButton(
        onClick = {
            when (etaRefreshState) {
                is EtaRefreshState.Enabled -> onRefresh()
                is EtaRefreshState.Disabled -> {
                    val secsSinceRequest =
                        (Calendar.getInstance().timeInMillis - etaRefreshState.timeDisabled) / 1000
                    Toast.makeText(
                        ctx,
                        "Refresh active in ${60 - secsSinceRequest} seconds",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            }
        },
        modifier = Modifier
            .height(40.dp)
            .padding(8.dp)
            .testTag(TestingTags.REFRESH_BUTTON),
        color = if (isEnabled) TriRailColors.Blue else Color.DarkGray,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_sync_48px),
                contentDescription = SB,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

const val NO_TRAINS = "No train eta at this time..."

@Composable
fun ShowRouteInfo(
    title: String,
    arrival: TrainArrival?,
    goToTrainSchedule: (String) -> Unit
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
                if (it !is TrainArrival.EndOfLine) {
                    Text(
                        modifier = Modifier,
                        text = title,
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

            }
            Spacer(modifier = Modifier.padding(vertical = 2.dp))

            when (it) {
                is TrainArrival.EstimatedArrival -> {
                    RouteInfo(it.info.toEtaString(), it.trackNumber.toString(), it.trainId)
                }

                is TrainArrival.EndOfLine -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .padding(horizontal = 20.dp)
                            .padding(vertical = 2.dp)
                            .testTag(TestingTags.END_OF_LINE),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = CenterVertically
                    ) {
                        TriRailButton(
                            Modifier.fillMaxWidth(),
                            onClick = { goToTrainSchedule("S") }
                        ) {
                            Text(
                                "See all departures", style = TextStyle(
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }
                    }
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
                            modifier = Modifier,
//                                .testTag(if (direction == "North") ETA_TITLE_NORTH else ETA_TITLE_SOUTH),
                            text = NO_TRAINS,
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                    }
                }
                else -> {}
            }
        }
    }

}

@Composable
fun RouteInfo(info: String, trackNumber: String? = null, trainId: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(TestingTags.TRAIN_ARRIVAL),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier.testTag(TestingTags.TRAIN_ARRIVAL_TRACK)
        ) {

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
                modifier = Modifier.testTag(TestingTags.TRAIN_ARRIVAL_INFO),
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