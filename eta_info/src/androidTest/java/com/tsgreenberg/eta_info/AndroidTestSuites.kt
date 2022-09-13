package com.tsgreenberg.eta_info

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.tsgreenberg.eta_info.EndToEndTesting.EtaInfoEndToEndTest
import com.tsgreenberg.eta_info.ComponentTesting.RefreshButtonTests
import com.tsgreenberg.eta_info.ComponentTesting.RouteInfoTests
import org.junit.runner.RunWith
import org.junit.runners.Suite

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@RunWith(Suite::class)
@Suite.SuiteClasses(
    RefreshButtonTests::class,
    RouteInfoTests::class,
    EtaInfoEndToEndTest::class
)
class EtaUiTestSuite