package com.tsgreenberg.fm_eta.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.tsgreenberg.fm_eta.R
import com.tsgreenberg.fm_eta.models.EtaRefreshState
import com.tsgreenberg.fm_eta.models.Keys.NORTH_KEY
import com.tsgreenberg.fm_eta.models.Keys.SOUTH_KEY
import com.tsgreenberg.fm_eta.models.TrainArrival
import com.tsgreenberg.fm_eta.models.TrainInfoState
import com.tsgreenberg.fm_eta.utils.TestingTags
import com.tsgreenberg.ui_components.*
import kotlinx.coroutines.delay
import java.util.*


@Composable
fun EtaScreen(
    shortName: String = "",
    state: TrainInfoState,
    refresh: (Int, Long) -> Unit,
    goToTrainSchedule: () -> Unit,
) {
    TriRailScaffold(
        extraText = shortName,
        progressBarState = state.etaProgressBarState,
        error = state.error,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .testTag("root"),
            contentAlignment = Alignment.Center
        ) {
            EtaContentScreen(
                state.arrivalMap,
                state.refreshId,
                state.etaRefreshState,
                onRefresh = refresh,
                goToTrainSchedule = goToTrainSchedule,
            )
        }


    }
}

const val NB = "Northbound Schedule"
const val SB = "Southbound Schedule"

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EtaInfoContainer(
    title: String,
    arrivals: List<TrainArrival>,
    animationDelay: Long = 5000,
) {
    var shouldAnimate by remember { mutableStateOf(true) }
    Column(Modifier.pointerInput(Unit) {
        detectTapGestures(
            onPress = {
                if (shouldAnimate) shouldAnimate = false
            },
        )
    }) {
        val pagerState = rememberPagerState()

        HorizontalPager(count = arrivals.size, state = pagerState) { i ->
            EtaInfoRow(
                title, arrivals[i]
            )
        }

        if (arrivals.size > 1) {
            HorizontalPagerIndicator(
                pagerState,
                activeColor = Color.White,
                indicatorHeight = 5.dp,
                indicatorWidth = 5.dp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            if (shouldAnimate) {
                LaunchedEffect(key1 = Unit) {
                    repeat(arrivals.size + 1) {
                        delay(animationDelay)
                        val targetPage = it % arrivals.size
                        pagerState.animateScrollToPage(targetPage)
                    }
                }
            }
        }
    }
}

const val SOUTHBOUND_ETA = "Southbound ETA"
const val NORTHBOUND_ETA = "Northbound ETA"

fun List<TrainArrival>.canShow(): Boolean = isNotEmpty() && first() is TrainArrival.EstimatedArrival


@Composable
fun EtaContentScreen(
    enRouteMap: Map<String, List<TrainArrival>>,
    refreshId: Int,
    etaRefreshState: EtaRefreshState,
    onRefresh: (Int, Long) -> Unit,
    goToTrainSchedule: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 22.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier.weight(4f, true),
            verticalArrangement = Arrangement.Center,
        ) {
            val northTrains = enRouteMap[NORTH_KEY]
            val southTrains = enRouteMap[SOUTH_KEY]

            if (northTrains != null && southTrains != null) {
                if (northTrains.canShow() || southTrains.canShow()) {
                    EstimatedArrivalScreen(northTrains, southTrains)
                } else {
                    Text(text = "No information available")
                }
            }


        }
        Row(
            Modifier.weight(1f, true),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TriRailButton(
                modifier = Modifier
                    .size(34.dp)
                    .testTag(TestingTags.REFRESH_BUTTON),
                onClick = goToTrainSchedule
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_departures),
                        contentDescription = SB,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(4.dp))
            RefreshButton(refreshId, etaRefreshState, onRefresh = onRefresh)
        }
    }
}

@Composable
fun ColumnScope.EstimatedArrivalScreen(
    northTrains: List<TrainArrival>,
    southTrains: List<TrainArrival>
) {
    if (northTrains.canShow()) {
        EtaInfoContainer(
            title = NORTHBOUND_ETA,
            arrivals = northTrains,
        )
    }

    if (northTrains.canShow() && southTrains.canShow()) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 4.dp)
                .height(1.dp)
                .width(40.dp)
                .background(color = Color.White)
        )
    }

    if (southTrains.canShow()) {
        EtaInfoContainer(
            title = SOUTHBOUND_ETA,
            arrivals = southTrains,
            animationDelay = 5500,
        )
    }
}


@Composable
fun RefreshButton(
    refreshId: Int, etaRefreshState: EtaRefreshState, onRefresh: (Int, Long) -> Unit
) {
    val isEnabled = etaRefreshState is EtaRefreshState.Enabled
    val ctx = LocalContext.current
    TriRailButton(
        onClick = {
            when (etaRefreshState) {
                is EtaRefreshState.Enabled -> onRefresh(
                    refreshId, Calendar.getInstance().timeInMillis
                )
                is EtaRefreshState.Disabled -> {
                    val secsSinceRequest =
                        (Calendar.getInstance().timeInMillis - etaRefreshState.timeDisabled) / 1000
                    Toast.makeText(
                        ctx,
                        "Refresh active in ${60 - secsSinceRequest} seconds",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        },
        modifier = Modifier
            .size(34.dp)
            .testTag(TestingTags.REFRESH_BUTTON),
        color = if (isEnabled) TriRailColors.Blue else Color.DarkGray,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_sync_48px),
                contentDescription = SB,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun ArrivalInfoHeader(title: String, arrival: TrainArrival) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        if (arrival !is TrainArrival.EndOfLine) {
            Text(
                modifier = Modifier, text = title, style = TextStyle(
                    color = Color.White, fontSize = 12.sp
                )
            )
            if (arrival is TrainArrival.EstimatedArrival) {
                arrival.status?.let { status ->
                    Text(
                        text = " $status", style = TextStyle(
                            color = Color(android.graphics.Color.parseColor(arrival.statusColor)),
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }

    }
}

@Composable
fun ArrivalInfo(arrival: TrainArrival) = when (arrival) {
    is TrainArrival.EstimatedArrival -> {
        EtaInfo(arrival.info.toEtaString(), arrival.trackNumber.toString(), arrival.trainId)
    }

    is TrainArrival.EndOfLine -> {
        // nothing at the moment
    }

    is TrainArrival.NoInformation -> {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .testTag(TestingTags.ARRIVAL_INFO_NO_INFO),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = NO_TRAINS, style = TextStyle(
                    color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold
                )
            )

        }
    }
}


const val NO_TRAINS = "No train eta at this time..."

@Composable
fun EtaInfoRow(
    title: String, arrival: TrainArrival
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(title),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ArrivalInfoHeader(title, arrival)
        Spacer(modifier = Modifier.padding(vertical = 2.dp))
        ArrivalInfo(arrival)
    }

}

@Composable
fun EtaInfo(info: String, trackNumber: String? = null, trainId: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(TestingTags.ARRIVAL_INFO_fm_eta),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier.testTag(TestingTags.TRAIN_ARRIVAL_TRACK)
        ) {

            TrackArrow(
                fontSize = 10.sp, trackTxt = trainId
            )
            trackNumber?.let {
                Text(
                    text = "Track #$it", style = TextStyle(
                        color = Color.White, fontSize = 12.sp
                    )
                )
            }

        }
        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                modifier = Modifier.testTag(TestingTags.TRAIN_ARRIVAL_INFO),
                text = info,
                style = TextStyle(
                    color = Color.White, fontSize = 18.sp, lineHeight = 0.sp, letterSpacing = 0.sp
                ),
                textAlign = TextAlign.End
            )
        }

    }

}