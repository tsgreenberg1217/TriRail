package com.tsgreenberg.fm_eta.EndToEndTesting

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.tsgreenberg.fm_eta.android_test_utils.EtaInfoEndToEndTestBase
import com.tsgreenberg.fm_eta.android_test_utils.EtaMockData
import com.tsgreenberg.fm_eta.di.ActivityModule
import com.tsgreenberg.fm_eta.remote_classes.EtaService
import com.tsgreenberg.fm_eta.MockEtaService
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@UninstallModules(ActivityModule::class)
@HiltAndroidTest
class EtaLifecycleTest : EtaInfoEndToEndTestBase() {
    @BindValue
    @JvmField
    val service: EtaService = MockEtaService(EtaMockData.available)

}