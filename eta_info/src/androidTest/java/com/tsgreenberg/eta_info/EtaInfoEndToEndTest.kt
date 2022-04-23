package com.tsgreenberg.eta_info

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import com.tsgreenberg.eta_info.android_test_utils.EtaMockData
import com.tsgreenberg.eta_info.remote_classes.EtaService
import com.tsgreenberg.eta_info.testing.MockEtaService
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_MINS_NORTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_MINS_SOUTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_TITLE_NORTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_TITLE_SOUTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_VIEWPAGER
import com.tsgreenberg.eta_info.testing.TestingTags.NO_TRAIN_NORTH
import com.tsgreenberg.eta_info.testing.TestingTags.NO_TRAIN_SOUTH
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@RunWith(Suite::class)
@Suite.SuiteClasses(
    EtaInfoEndToEndTest::class,
    EtaInfoEndToEndTestUnAvail::class
)
class EtaUiTestSuite

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@HiltAndroidTest
class EtaInfoEndToEndTest : EtaInfoEndToEndTestBase() {
    @BindValue
    @JvmField
    val service: EtaService = MockEtaService(EtaMockData.available)

    @Test
    fun bothTrainsAvailable() {
        composeTestRule.also {

            it.onNodeWithTag(ETA_TITLE_SOUTH, useUnmergedTree = true)
                .assertTextEquals("Southbound")

            it.onNodeWithTag(ETA_MINS_SOUTH, useUnmergedTree = true)
                .assertTextEquals("7 mins")

            it.onNodeWithTag(ETA_VIEWPAGER, useUnmergedTree = true)
                .performTouchInput { swipeLeft() }
            it.onNodeWithTag(ETA_TITLE_NORTH, useUnmergedTree = true)
                .assertTextEquals("Northbound")

            it.onNodeWithTag(ETA_MINS_NORTH, useUnmergedTree = true)
                .assertTextEquals("20 mins")
        }
    }
}



@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@HiltAndroidTest
class EtaInfoEndToEndTestUnAvail : EtaInfoEndToEndTestBase() {
    @BindValue
    @JvmField
    val service: EtaService = MockEtaService(EtaMockData.notAvailable)

    @Test
    fun bothTrainsNotAvailable() {
        composeTestRule.also {

            it.onNodeWithTag(ETA_TITLE_SOUTH, useUnmergedTree = true)
                .assertTextEquals("Southbound")

            it.onNodeWithTag(NO_TRAIN_SOUTH, useUnmergedTree = true)
                .assertTextEquals("No upcoming trains for today.")

            it.onNodeWithTag(ETA_VIEWPAGER, useUnmergedTree = true)
                .performTouchInput { swipeLeft() }

            it.onNodeWithTag(ETA_TITLE_NORTH, useUnmergedTree = true)
                .assertTextEquals("Northbound")

            it.onNodeWithTag(NO_TRAIN_NORTH, useUnmergedTree = true)
                .assertTextEquals("No upcoming trains for today.")
        }
    }
}