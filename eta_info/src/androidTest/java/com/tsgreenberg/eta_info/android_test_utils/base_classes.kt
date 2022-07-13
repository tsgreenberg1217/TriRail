package com.tsgreenberg.eta_info.android_test_utils

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.test.platform.app.InstrumentationRegistry
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.eta_info.di.EtaInfoNavigationQualifier
import com.tsgreenberg.eta_info.models.EtaInfoViewModelCache
import com.tsgreenberg.eta_info.models.TrainScheduleDto
import com.tsgreenberg.eta_info.remote_classes.TrainScheduleService
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


class TestScheduleService : TrainScheduleService{
    override suspend fun getScheduleForStation(
        stationId: Int,
        direction: String,
        isWeekday: Boolean
    ): List<TrainScheduleDto> {
        return listOf()
    }

}

open class EtaInfoEndToEndTestBase {

}