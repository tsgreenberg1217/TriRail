package com.tsgreenberg.eta_info.di

import com.tsgreenberg.eta_info.remote_classes.EtaInteractors
import com.tsgreenberg.eta_info.remote_classes.EtaService
import com.tsgreenberg.eta_info.remote_classes.GetEtaForStation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class ActivityModule{

    @Provides
    @ActivityRetainedScoped
    fun getGetEtaForStation(interactors: EtaInteractors): GetEtaForStation {
        return interactors.getEtaForStation
    }


    @Provides
    @ActivityRetainedScoped
    fun getEtaInteractors(
        etaService: EtaService
    ): EtaInteractors {
        return EtaInteractors.build(etaService)
    }
}