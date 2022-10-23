package com.tsgreenberg.eta_info.di

import com.tsgreenberg.eta_info.mappers.EtaDtoMapper
import com.tsgreenberg.eta_info.remote_classes.*
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
    fun providesGetGetEtaForStation(interactors: EtaInteractors): GetEtaForStation {
        return interactors.getEtaForStation
    }

    @Provides
    @ActivityRetainedScoped
    fun providesGetEtaInteractors(
        etaService: EtaService,
        etaDtoMapper: EtaDtoMapper
    ): EtaInteractors = EtaInteractors.build(etaService, etaDtoMapper)



}