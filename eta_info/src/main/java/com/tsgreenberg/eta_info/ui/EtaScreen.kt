package com.tsgreenberg.eta_info.ui

import android.graphics.RectF
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import com.google.accompanist.pager.*
import com.tsgreenberg.core.navigation.TriRailNav
import com.tsgreenberg.eta_info.EnRouteInfo
import com.tsgreenberg.eta_info.models.EtaStationState
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_MINS_NORTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_MINS_SOUTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_TITLE_NORTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_TITLE_SOUTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_VIEWPAGER
import com.tsgreenberg.eta_info.testing.TestingTags.NO_TRAIN_NORTH
import com.tsgreenberg.eta_info.testing.TestingTags.NO_TRAIN_SOUTH
import com.tsgreenberg.ui_components.TriRailScaffold
import com.tsgreenberg.ui_components.ViewPagerScroll

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EtaScreen(
    triRailNav: TriRailNav,
    state: EtaStationState
) {
    val items = 2
    Log.d("WEAROS_TEST", "recomp in screen")
    val scalingLazyListState = rememberScalingLazyListState()
    TriRailScaffold(
        scalingLazyListState = scalingLazyListState,
        state.progressBarState
    ) {

        val scrollState = rememberScrollableState {
            println(it)
            it
        }
        state.eta?.let {
            val enRouteMap = it.etaMap
            val pagerState = rememberPagerState()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .scrollable(scrollState, orientation = Orientation.Vertical),
                contentAlignment = Alignment.Center
            ) {
                ViewPagerScroll(pagerState = pagerState)
                VerticalPager(
                    enRouteMap.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(ETA_VIEWPAGER),
                    userScrollEnabled = true
                ) { page ->
                    val key = if (page == 0) "South" else "North"
                    Column {
                        Spacer(modifier = Modifier.padding(16.dp))

//                        Text(
//                            modifier = Modifier
//                                .background(Color(0xFF4E90A6))
//                                .fillMaxWidth()
//                                .testTag(if (key == "North") ETA_TITLE_NORTH else ETA_TITLE_SOUTH),
//                            text = "${key}bound",
//                            style = TextStyle(
//                                color = Color.White,
//                                textAlign = TextAlign.Center
//                            )
//                        )

                        enRouteMap[key]?.let {
                            if (it.isEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,

                                    ) {
                                    Text(
                                        modifier = Modifier.testTag(if (key == "North") NO_TRAIN_NORTH else NO_TRAIN_SOUTH),
                                        text = "No upcoming trains for today."
                                    )
                                    Spacer(modifier = Modifier.padding(10.dp))
                                }
                            } else {
                                RouteInfoScreen(
                                    info = it.first(),
                                    if (key == "North") ETA_MINS_NORTH else ETA_MINS_SOUTH
                                )
                            }
                        }

                    }
                }

            }
        }

    }
}

@Composable
fun RouteInfoScreen(info: EnRouteInfo, key: String) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = "ETA", style = TextStyle(
                color = Color.White,
                fontSize = 14.sp
            )
        )
        Text(
            modifier = Modifier.testTag(key),
            text = "${info.minutes} mins", style = TextStyle(
                color = Color.White,
                fontSize = 32.sp
            )
        )
    }
//    Spacer(modifier = Modifier.fillMaxWidth())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Text(
            modifier = Modifier.weight(1f),
            text = "Track",
            textAlign = TextAlign.Center
        )
//
//        TrackArrow(
//            modifier = Modifier
//                .weight(1f)
//                .height(30.dp),
//            "#${info.track}"
//        )
    }

}