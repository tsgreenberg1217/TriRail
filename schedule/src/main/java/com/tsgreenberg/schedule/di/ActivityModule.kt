package com.tsgreenberg.schedule.di

import com.tsgreenberg.schedule.GetTrainSchedulesForStation
import com.tsgreenberg.schedule.TrainScheduleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@Module
@InstallIn(ActivityRetainedComponent::class)
class ActivityModule {
//
//    @Provides
//    @ActivityRetainedScoped
//    fun providesGetEtaInteractors(
//        etaService: EtaService,
//        trainServices: com.tsgreenberg.schedule.TrainScheduleService,
//        etaDtoMapper: EtaDtoMapper
//    ): EtaInteractors = EtaInteractors.build(etaService, trainServices, etaDtoMapper)


    @Provides
    @ActivityRetainedScoped
    fun providesGetSchedulesForStation(
        service:TrainScheduleService
    ):GetTrainSchedulesForStation = GetTrainSchedulesForStation(service)

}