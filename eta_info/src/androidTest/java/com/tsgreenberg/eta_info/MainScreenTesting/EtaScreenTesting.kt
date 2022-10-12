package com.tsgreenberg.eta_info.MainScreenTesting

import androidx.activity.ComponentActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.tsgreenberg.core.ProgressBarState
import com.tsgreenberg.eta_info.MockEtaService
import com.tsgreenberg.eta_info.android_test_utils.EtaMockData
import com.tsgreenberg.eta_info.di.ActivityModule
import com.tsgreenberg.eta_info.models.TrainInfoState
import com.tsgreenberg.eta_info.remote_classes.EtaService
import com.tsgreenberg.eta_info.ui.screens.EtaScreen
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@UninstallModules(ActivityModule::class)
@HiltAndroidTest
class EtaScreenTesting {

    @BindValue
    @JvmField
    val service: EtaService = MockEtaService(EtaMockData.available, true)

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    var refreshTriggered = false
    var navigationTriggered = false


    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    val state = mutableStateOf(TrainInfoState())

    @Before
    fun before() {
        hiltRule.inject()
    }

//    loading_spinner_test

    fun setUpScreen() = composeTestRule.apply {
        setContent {
            EtaScreen(
                state = state.value,
                refresh = { _, _ -> refreshTriggered = true },
                goToTrainSchedule = { navigationTriggered = true }
            )
        }


    }

    @Test
    fun shouldShowLoadingSpinner() {
        state.value = state.value.copy(
            etaProgressBarState = ProgressBarState.Loading
        )
        setUpScreen().onNodeWithTag("loading_spinner_test").assertIsDisplayed()
    }


    @Test
    fun shoulHideLoadingSpinner() {
        state.value = state.value.copy(
            etaProgressBarState = ProgressBarState.Idle
        )
        setUpScreen().onNodeWithTag("loading_spinner_test").assertDoesNotExist()
    }


}