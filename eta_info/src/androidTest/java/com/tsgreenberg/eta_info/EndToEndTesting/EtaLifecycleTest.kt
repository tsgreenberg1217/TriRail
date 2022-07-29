package com.tsgreenberg.eta_info.EndToEndTesting

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.tsgreenberg.eta_info.android_test_utils.EtaInfoEndToEndTestBase
import com.tsgreenberg.eta_info.android_test_utils.EtaMockData
import com.tsgreenberg.eta_info.di.ActivityModule
import com.tsgreenberg.eta_info.remote_classes.EtaService
import com.tsgreenberg.eta_info.testing.MockEtaService
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