package com.tsgreenberg.eta_info.EndToEndTesting

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import com.tsgreenberg.eta_info.android_test_utils.EtaInfoEndToEndTestBase
import com.tsgreenberg.eta_info.android_test_utils.EtaMockData
import com.tsgreenberg.eta_info.di.ActivityModule
import com.tsgreenberg.eta_info.remote_classes.EtaService
import com.tsgreenberg.eta_info.MockEtaService
import com.tsgreenberg.eta_info.utils.TestingTags
import com.tsgreenberg.eta_info.utils.TestingTags.ETA_VIEWPAGER
import com.tsgreenberg.eta_info.ui.screens.NB
import com.tsgreenberg.eta_info.ui.screens.SB
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Test


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
//
//@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
//@RunWith(Suite::class)
//@Suite.SuiteClasses(
//    EtaInfoEndToEndTest::class,
////    EtaInfoEndToEndTestUnAvail::class
//)
//class EtaUiTestSuite

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@UninstallModules(ActivityModule::class)
@HiltAndroidTest
class EtaInfoEndToEndTest : EtaInfoEndToEndTestBase() {


    @BindValue
    @JvmField
    val service: EtaService = MockEtaService(EtaMockData.available)

    @Test
    fun bothTrainsAvailable() {
        composeTestRule.also {
            it.onNodeWithTag(TestingTags.ARRIVAL_INFO_END_OF_LINE)
                .assertExists()
                .onChildren()
                .assertAny(hasText("See all departures"))


            it.onNodeWithTag(TestingTags.ARRIVAL_INFO_ETA_INFO)
                .assertExists()

            it.onNodeWithTag(TestingTags.TRAIN_ARRIVAL_TRACK,true)
                .assertExists()
                .onChildAt(1)
                .assertTextEquals("Track #1")

            it.onNodeWithTag(TestingTags.TRAIN_ARRIVAL_INFO,true)
                .assertExists()
                .assertTextEquals("20 mins")

            it.onNodeWithTag(ETA_VIEWPAGER, useUnmergedTree = true)
                .performTouchInput { swipeUp() }

            it.onNodeWithTag(TestingTags.NORTH_SCHEDULE_BUTTON)
                .assertExists()
                .assertHasClickAction()
                .assert(hasText(NB))

            it.onNodeWithTag(TestingTags.SOUTH_SCHEDULE_BUTTON)
                .assertExists()
                .assertHasClickAction()
                .assert(hasText(SB))
                .performClick()

            it.onNodeWithTag(TestingTags.SCHEDULE_LIST).assertExists()


        }
    }
}

