package com.tsgreenberg.station_list.di

import com.tsgreenberg.station_list.GetStops
import com.tsgreenberg.station_list.StationInteractors
import com.tsgreenberg.station_list.StationsService
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