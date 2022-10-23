package com.tsgreenberg.fm_stations.di

import com.tsgreenberg.fm_stations.interactors.StationInteractors
import com.tsgreenberg.fm_stations.remote.StationsService
import com.tsgreenberg.fm_stations.use_cases.GetStops
import com.tsgreenberg.stationlist.StationQueries
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
    fun getGetStops(interactors: StationInteractors): GetStops = interactors.getStops

    @Provides
    @ActivityRetainedScoped
    fun getStationInteractors(
        stationService: StationsService,
        stationQueries: StationQueries
    ): StationInteractors {
        return StationInteractors.build(stationService, stationQueries)
    }


}