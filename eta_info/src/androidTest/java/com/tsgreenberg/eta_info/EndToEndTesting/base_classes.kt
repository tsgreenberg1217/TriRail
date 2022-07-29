package com.tsgreenberg.eta_info.android_test_utils

import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.navigation.NavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.core.navigation.TriRailRootAction
import com.tsgreenberg.eta_info.di.EtaInfoNavigationQualifier
import com.tsgreenberg.eta_info.models.TrainScheduleDto
import com.tsgreenberg.eta_info.remote_classes.*
import com.tsgreenberg.eta_info.testing.MockNavigation
import com.tsgreenberg.eta_info.ui.activity.EtaInfoActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject


class TestScheduleService : TrainScheduleService {
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
//
//    @get:Rule(order = 1)
//    val composeTestRule = createAndroidComposeRule<EtaInfoActivity>()


    @get:Rule(order = 1)
    val composeTestRule = createEmptyComposeRule()


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