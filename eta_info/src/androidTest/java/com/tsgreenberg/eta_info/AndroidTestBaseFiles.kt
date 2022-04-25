package com.tsgreenberg.eta_info

import com.tsgreenberg.eta_info.di.ActivityModule
import com.tsgreenberg.eta_info.remote_classes.EtaInteractors
import com.tsgreenberg.eta_info.remote_classes.EtaService
import com.tsgreenberg.eta_info.remote_classes.GetEtaForStation
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

//
//@Module
//@TestInstallIn(components = [SingletonComponent::class], replaces = [ActivityModule::class])
//class FakeEtaModule {
//
//    @Provides
//    fun getEtaInteractors(
//        etaService: EtaService
//    ): EtaInteractors {
//        return EtaInteractors.build(etaService)
//    }
//
//    @Provides
//    fun getGetEtaForStation(interactors: EtaInteractors): GetEtaForStation {
//        return interactors.getEtaForStation
//    }
//}
//
