package com.tsgreenberg.fm_eta

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.tsgreenberg.fm_eta.EndToEndTesting.EtaInfoEndToEndTest
import com.tsgreenberg.fm_eta.ComponentTesting.RefreshButtonTests
import com.tsgreenberg.fm_eta.ComponentTesting.RouteInfoTests
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