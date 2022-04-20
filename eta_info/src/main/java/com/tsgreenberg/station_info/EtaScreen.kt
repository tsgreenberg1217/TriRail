package com.tsgreenberg.station_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.navigation.TriRailNav
import com.tsgreenberg.station_list.TrackArrow
import com.tsgreenberg.ui_components.TriRailScaffold

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EtaScreen(
    triRailNav: TriRailNav,
    id: Int
) {
    val viewModel: StationDetailViewModel = hiltViewModel()
    viewModel.setStationEta(id)
    val scalingLazyListState = rememberScalingLazyListState()
    TriRailScaffold(scalingLazyListState = scalingLazyListState) {
        when (val state = viewModel.state.value) {
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
                val enRouteMap = state.data.etaMap

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    val pagerState = rememberPagerState()
                    HorizontalPager(
                        enRouteMap.size,
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        val key = if (page == 0) "South" else "North"
                        Column {
                            Spacer(modifier = Modifier.padding(16.dp))

                            Text(
                                modifier = Modifier
                                    .background(Color(0xFF4E90A6))
                                    .fillMaxWidth(),
                                text = "${key}bound",
                                style = TextStyle(
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            )

//                            Spacer(modifier = Modifier.padding(16.dp))
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
                                    RouteInfoScreen(info = it.first())
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

            is DataState.Error -> {

            }
        }
    }
}

@Composable
fun RouteInfoScreen(info: EnRouteInfo) {

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

        TrackArrow(
            modifier = Modifier
                .weight(1f)
                .height(30.dp),
            "#${info.track}"
        )
    }

}