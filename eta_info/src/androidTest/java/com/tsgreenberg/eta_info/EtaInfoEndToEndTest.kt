package com.tsgreenberg.eta_info

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.test.platform.app.InstrumentationRegistry
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.eta_info.android_test_utils.EtaInfoEndToEndTestBase
import com.tsgreenberg.eta_info.android_test_utils.EtaMockData
import com.tsgreenberg.eta_info.android_test_utils.TestScheduleService
import com.tsgreenberg.eta_info.di.ActivityModule
import com.tsgreenberg.eta_info.di.EtaInfoNavigationQualifier
import com.tsgreenberg.eta_info.models.EtaInfoViewModelCache
import com.tsgreenberg.eta_info.remote_classes.*
import com.tsgreenberg.eta_info.testing.MockEtaService
import com.tsgreenberg.eta_info.testing.MockNavigation
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_MINS_NORTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_MINS_SOUTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_TITLE_NORTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_TITLE_SOUTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_VIEWPAGER
import com.tsgreenberg.eta_info.testing.TestingTags.NO_TRAIN_NORTH
import com.tsgreenberg.eta_info.testing.TestingTags.NO_TRAIN_SOUTH
import com.tsgreenberg.eta_info.ui.activity.EtaInfoActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite


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


    @Module
    @InstallIn(SingletonComponent::class)
    abstract class TestModule {
        @Binds
        @EtaInfoNavigationQualifier
        abstract fun getTestNav(mockNavigation: MockNavigation): TriRailNavImplementor<NavHostController>

    }

    @Module
    @InstallIn(ActivityRetainedComponent::class)
    class TestActivityModule {

        @Provides
        @ActivityRetainedScoped
        fun getVmCache(): EtaInfoViewModelCache = EtaInfoViewModelCache().apply {
            stationId = 1
        }
        @Provides
        @ActivityRetainedScoped
        fun providesGetGetEtaForStation(interactors: EtaInteractors): GetEtaForStation {
            return interactors.getEtaForStation
        }

        @Provides
        @ActivityRetainedScoped
        fun providesGetTrainSchedulesForStation(interactors: EtaInteractors): GetTrainSchedulesForStation {
            return interactors.getTrainSchedulesForStation
        }
        @Provides
        @ActivityRetainedScoped
        fun providesGetEtaInteractors(
            etaService: EtaService,
            trainServices: TrainScheduleService
        ): EtaInteractors {
            return EtaInteractors.build(etaService, trainServices)
        }


        @Provides
        @ActivityRetainedScoped
        fun getDatabase(): TrainScheduleService = TestScheduleService()

    }

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<EtaInfoActivity>()


    private val context =
        InstrumentationRegistry.getInstrumentation().targetContext.applicationContext

    @Before
    fun before() {
        hiltRule.inject()
//        composeTestRule.activity.apply {
//            val testViewModel: StationDetailViewModel by viewModels()
//            testViewModel.setStationEta(1)
//        }
    }


    //// TESTS



    @BindValue
    @JvmField
    val service: EtaService = MockEtaService(EtaMockData.available)

//    @BindValue
//    @JvmField
//    val firebase:TrainScheduleService = TestScheduleService()

    @Test
    fun bothTrainsAvailable() {
        composeTestRule.also {
//            it.onNodeWithTag(ETA_TITLE_SOUTH, useUnmergedTree = true)
//                .assertTextEquals("Southbound")
//
//            it.onNodeWithTag(ETA_MINS_SOUTH, useUnmergedTree = true)
//                .assertTextEquals("7 mins")

            it.onNodeWithTag(ETA_VIEWPAGER, useUnmergedTree = true)
                .performTouchInput { swipeUp() }

//            it.onNodeWithTag(ETA_TITLE_NORTH, useUnmergedTree = true)
//                .assertTextEquals("Northbound")
//
//            it.onNodeWithTag(ETA_MINS_NORTH, useUnmergedTree = true)
//                .assertTextEquals("20 mins")
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