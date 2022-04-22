package com.tsgreenberg.eta_info.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.tsgreenberg.core.navigation.TriRailNav
import com.tsgreenberg.eta_info.EnRouteInfo
import com.tsgreenberg.eta_info.models.EtaStationState
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_MINS_NORTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_MINS_SOUTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_TITLE_NORTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_TITLE_SOUTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_VIEWPAGER
import com.tsgreenberg.ui_components.TriRailScaffold

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EtaScreen(
    triRailNav: TriRailNav,
    state: EtaStationState
) {
    Log.d("WEAROS_TEST", "recomp in screen")
    val scalingLazyListState = rememberScalingLazyListState()
    TriRailScaffold(
        scalingLazyListState = scalingLazyListState,
        state.progressBarState
    ) {

        state.eta?.let {
            val enRouteMap = it.etaMap
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val pagerState = rememberPagerState()
                HorizontalPager(
                    enRouteMap.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(ETA_VIEWPAGER)
                ) { page ->
                    val key = if (page == 0) "South" else "North"
                    Column {
                        Spacer(modifier = Modifier.padding(16.dp))

                        Text(
                            modifier = Modifier
                                .background(Color(0xFF4E90A6))
                                .fillMaxWidth()
                                .testTag(if (key == "North") ETA_TITLE_NORTH else ETA_TITLE_SOUTH),
                            text = "${key}bound",
                            style = TextStyle(
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        )

                        enRouteMap[key]?.let {
                            if (it.isEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,

                                    ) {
                                    Text(text = "No upcoming trains for today.")
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

                if (pagerState.pageCount > 0) {
                    HorizontalPagerIndicator(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(10.dp),
                        pagerState = pagerState,
                        activeColor = Color.White,
                    )
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