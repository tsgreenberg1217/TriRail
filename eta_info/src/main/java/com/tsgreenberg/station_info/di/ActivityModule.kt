package com.tsgreenberg.station_info.di

import com.tsgreenberg.station_info.EtaInteractors
import com.tsgreenberg.station_info.GetEtaForStation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ActivityModule{
    @Provides
    fun getGetEtaForStation(interactors: EtaInteractors): GetEtaForStation {
        return interactors.getEtaForStation
    }
}