package com.tsgreenberg.fm_eta.android_test_utils

import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.navigation.NavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.core.navigation.TriRailRootAction
import com.tsgreenberg.fm_eta.di.EtaInfoNavigationQualifier
import com.tsgreenberg.fm_eta.models.TrainScheduleDto
import com.tsgreenberg.fm_eta.remote_classes.*
import com.tsgreenberg.fm_eta.MockNavigation
import com.tsgreenberg.eta_info.mappers.EtaDtoMapper
import com.tsgreenberg.fm_eta.ui.activity.EtaInfoActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule


class TestScheduleService : com.tsgreenberg.schedule.TrainScheduleService {
    override suspend fun getScheduleForStation(
        stationId: Int,
        direction: String,
        isWeekday: Boolean
    ): List<TrainScheduleDto> {
        return listOf()
    }

}

open class EtaInfoEndToEndTestBase {
    private lateinit var scenario: ActivityScenario<EtaInfoActivity>

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
            trainServices: com.tsgreenberg.schedule.TrainScheduleService,
            etaMapper: EtaDtoMapper
        ): EtaInteractors {
            return EtaInteractors.build(etaService, trainServices, etaMapper)
        }


        @Provides
        @ActivityRetainedScoped
        fun getDatabase(): com.tsgreenberg.schedule.TrainScheduleService = TestScheduleService()

    }

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    //
//    @get:Rule(order = 1)
    @get:Rule(order = 1)
    val composeTestRule = createEmptyComposeRule()


//    val composeTestRule = createAndroidComposeRule<EtaInfoActivity>()


//    private val context =
//        InstrumentationRegistry.getInstrumentation().targetContext.applicationContext

    @Before
    fun before() {
        hiltRule.inject()
        scenario = ActivityScenario.launch(
            createActivityIntent(
                InstrumentationRegistry.getInstrumentation().targetContext,
            )
        )
//        composeTestRule.activity.apply {
//            val testViewModel: StationDetailViewModel by viewModels()
//            testViewModel.setStationEta(1)
//        }
    }


    private fun createActivityIntent(
        context: Context
    ): Intent = Intent(context, EtaInfoActivity::class.java).apply {
        putExtra(TriRailRootAction.StationInfo.intentKey, 1)
    }


}