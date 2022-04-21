package com.tsgreenberg.eta_info

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.hilt.lifecycle.ViewModelFactoryModules
import androidx.test.platform.app.InstrumentationRegistry
import com.tsgreenberg.core.DataState
import com.tsgreenberg.eta_info.di.ActivityModule
import com.tsgreenberg.eta_info.di.MockEtaServiceQualifier
import com.tsgreenberg.eta_info.testing.MockEtaService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking

import org.junit.Test

import org.junit.Rule
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

//
//@Module
//@TestInstallIn(components = [SingletonComponent::class], replaces = [ActivityModule::class])
//class FakeEtaModule {
//
//    @Provides
//    @MockEtaServiceQualifier
//    fun getEtaService(): EtaService = MockEtaService()
//
//    @Provides
//    fun getEtaInteractors(
//        @MockEtaServiceQualifier etaService: EtaService
//    ): EtaInteractors {
//        return EtaInteractors.build(etaService)
//    }
//
//    @Provides
//    fun getGetEtaForStation(interactors: EtaInteractors): GetEtaForStation {
//        return interactors.getEtaForStation
//    }
//
//}
//
//
//@ExperimentalAnimationApi
//@ExperimentalComposeUiApi
//@HiltAndroidTest
//class EtaInfoTests {
//
//    // system under test
//    @Inject
//    lateinit var getEtaForStation: GetEtaForStation
//
//
//    @get:Rule(order = 0)
//    var hiltRule = HiltAndroidRule(this)
//
////    @get:Rule(order = 1)
////    val composeTestRule = createAndroidComposeRule<EtaInfoActivity>()
//
//    private val context = InstrumentationRegistry.getInstrumentation().targetContext
//
//
//    @Test
//    fun getEtaSuccess() = runBlocking {
//        val emissions = getEtaForStation.execute(12).toList()
//        assert(emissions[0] == DataState.Loading)
//        assert(emissions[1] is DataState.Success)
//    }
//}
