package com.tsgreenberg.eta_info

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import com.tsgreenberg.eta_info.android_test_utils.EtaInfoEndToEndTestBase
import com.tsgreenberg.eta_info.android_test_utils.EtaMockData
import com.tsgreenberg.eta_info.di.ActivityModule
import com.tsgreenberg.eta_info.remote_classes.EtaService
import com.tsgreenberg.eta_info.testing.MockEtaService
import com.tsgreenberg.eta_info.testing.TestingTags
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_VIEWPAGER
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
//            it.onNodeWithTag(ETA_TITLE_SOUTH, useUnmergedTree = true)
//                .assertTextEquals("Southbound")
//
            //
            it.onNodeWithTag(TestingTags.END_OF_LINE)
                .assertExists()
                .onChildren()
                .assertAny(hasText("See all departures"))

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


        }
    }
}

//
//
//@ExperimentalAnimationApi
//@ExperimentalComposeUiApi
//@HiltAndroidTest
//class EtaInfoEndToEndTestUnAvail : EtaInfoEndToEndTestBase() {
//    @BindValue
//    @JvmField
//    val service: EtaService = MockEtaService(EtaMockData.notAvailable)
//
//    @Test
//    fun bothTrainsNotAvailable() {
////        composeTestRule.also {
////
////            it.onNodeWithTag(ETA_TITLE_SOUTH, useUnmergedTree = true)
////                .assertTextEquals("Southbound")
////
////            it.onNodeWithTag(NO_TRAIN_SOUTH, useUnmergedTree = true)
////                .assertTextEquals("No upcoming trains for today.")
////
////            it.onNodeWithTag(ETA_VIEWPAGER, useUnmergedTree = true)
////                .performTouchInput { swipeLeft() }
////
////            it.onNodeWithTag(ETA_TITLE_NORTH, useUnmergedTree = true)
////                .assertTextEquals("Northbound")
////
////            it.onNodeWithTag(NO_TRAIN_NORTH, useUnmergedTree = true)
////                .assertTextEquals("No upcoming trains for today.")
////        }
//    }
//}