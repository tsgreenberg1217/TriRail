package com.tsgreenberg.station_list.di

import com.tsgreenberg.station_list.GetStops
import com.tsgreenberg.station_list.StationInteractors
import com.tsgreenberg.station_list.StationsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class ActivityModule {

    @Provides
    fun getGetStops(interactors: StationInteractors): GetStops = interactors.getStops

    @Provides
    fun getStationInteractors(stationService: StationsService): StationInteractors {
        return StationInteractors.build(stationService)
    }

}