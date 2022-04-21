package com.tsgreenberg.eta_info.di

import com.tsgreenberg.eta_info.EtaInteractors
import com.tsgreenberg.eta_info.EtaService
import com.tsgreenberg.eta_info.GetEtaForStation
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

    @Provides
    fun getEtaInteractors(
        etaService: EtaService
    ): EtaInteractors {
        return EtaInteractors.build(etaService)
    }
}