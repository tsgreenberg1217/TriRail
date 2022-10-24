package com.tsgreenberg.fm_schedule.di

import com.tsgreenberg.fm_schedule.mappers.TrainScheduleStateMapper
import com.tsgreenberg.fm_schedule.remote.TrainScheduleService
import com.tsgreenberg.fm_schedule.use_cases.GetTrainSchedulesForStation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@Module
@InstallIn(ActivityRetainedComponent::class)
class ActivityModule {

    @Provides
    @ActivityRetainedScoped
    fun providesGetSchedulesForStation(
        service: TrainScheduleService
    ): GetTrainSchedulesForStation = GetTrainSchedulesForStation(service)

    @Provides
    @ActivityRetainedScoped
    fun providesTrainScheduleStateMapper() = TrainScheduleStateMapper()

}