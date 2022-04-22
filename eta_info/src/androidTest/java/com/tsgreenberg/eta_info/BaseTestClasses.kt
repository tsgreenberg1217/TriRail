package com.tsgreenberg.eta_info

import androidx.activity.viewModels
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.test.platform.app.InstrumentationRegistry
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.eta_info.di.EtaInfoNavigationQualifier
import com.tsgreenberg.eta_info.testing.MockNavigation
import com.tsgreenberg.eta_info.ui.EtaInfoActivity
import com.tsgreenberg.eta_info.ui.StationDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule


//@Module
//@TestInstallIn(components = [SingletonComponent::class], replaces = [ActivityModule::class])
//class FakeEtaModule {
//
//
//    @Provides
//    fun getEtaInteractors(
//        etaService: EtaService
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

open class EtaInfoEndToEndTestBase {
    @Module
    @InstallIn(SingletonComponent::class)
    abstract class TestModule {
        @Binds
        @EtaInfoNavigationQualifier
        abstract fun getTestNav(mockNavigation: MockNavigation): TriRailNavImplementor<NavHostController>
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
        composeTestRule.activity.apply {
            val testViewModel: StationDetailViewModel by viewModels()
            testViewModel.setStationEta(1)
        }
    }

}