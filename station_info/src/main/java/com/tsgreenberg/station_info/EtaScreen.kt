package com.tsgreenberg.station_info

import android.util.Log
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.navigation.TriRailNav
import com.tsgreenberg.ui_components.TriRailScaffold
import java.util.*
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EtaScreen(
    triRailNav: TriRailNav, id: Int
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
                val enRouteMap = state.data[0].enRoute.getNextTrains()

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    val pagerState = rememberPagerState()
                    HorizontalPager(
                        2,
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxSize()
                    ) { page ->
                        val key = if (page == 0) "South" else "North"
                        enRouteMap[key]?.let { it[0] }?.let {
                            Column {
                                Spacer(modifier = Modifier.padding(16.dp))
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {

                                    val directionText = "${it.direction}bound"

                                    Text(
                                        modifier = Modifier
                                            .background(Color(0xFF4E90A6))
                                            .fillMaxWidth(),
                                        text = directionText,
                                        style = TextStyle(
                                            color = Color.White,
                                            textAlign = TextAlign.Center
                                        )
                                    )

                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize(),
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
                                            text = "${it.minutes} mins", style = TextStyle(
                                                color = Color.White,
                                                fontSize = 32.sp
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.fillMaxWidth())
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
}