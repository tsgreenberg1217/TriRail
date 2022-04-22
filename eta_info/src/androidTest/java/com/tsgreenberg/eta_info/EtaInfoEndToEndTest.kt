package com.tsgreenberg.eta_info

import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.test.platform.app.InstrumentationRegistry
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.eta_info.di.ActivityModule
import com.tsgreenberg.eta_info.di.EtaInfoNavigationQualifier
import com.tsgreenberg.eta_info.remote_classes.EtaInteractors
import com.tsgreenberg.eta_info.remote_classes.EtaService
import com.tsgreenberg.eta_info.remote_classes.GetEtaForStation
import com.tsgreenberg.eta_info.testing.MockEtaService
import com.tsgreenberg.eta_info.testing.MockNavigation
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_MINS_NORTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_MINS_SOUTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_TITLE_NORTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_TITLE_SOUTH
import com.tsgreenberg.eta_info.testing.TestingTags.ETA_VIEWPAGER
import com.tsgreenberg.eta_info.ui.EtaInfoActivity
import com.tsgreenberg.eta_info.ui.StationDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@HiltAndroidTest
class EtaInfoEndToEndTest : EtaInfoEndToEndTestBase() {
    @BindValue
    @JvmField
    val service: EtaService = MockEtaService()

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
