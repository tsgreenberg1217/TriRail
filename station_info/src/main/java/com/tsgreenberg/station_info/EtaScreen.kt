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
import androidx.compose.ui.unit.dp
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
//                val time = state.data[0].enRoute[0].time

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    val pagerState = rememberPagerState()
                    HorizontalPager(
                        2,
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxSize()
                    ) { page ->
                        Text(
                            text = "$page", style = TextStyle(
                                color = Color.White
                            )
                        )
                    }

                    if(pagerState.pageCount > 0){
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