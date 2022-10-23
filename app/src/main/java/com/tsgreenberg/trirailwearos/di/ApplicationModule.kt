package com.tsgreenberg.trirailwearos.di

import androidx.navigation.NavHostController
import com.tsgreenberg.core.navigation.TriRailNav
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.eta_info.di.EtaInfoNavigationQualifier
import com.tsgreenberg.station_list.di.StationListNavigationQualifier
import com.tsgreenberg.trirailwearos.EtaInfoNavigator
import com.tsgreenberg.trirailwearos.StationListNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ApplicationModule {

    @Binds
    @ActivityRetainedScoped
    @StationListNavigationQualifier
    abstract fun bindsStationListNavigator(
        stationListNavigation: StationListNavigator
    ): TriRailNav

    @Binds
    @ActivityRetainedScoped
    @EtaInfoNavigationQualifier
    abstract fun bindsEtaInfoNavigator(
        etaInfoNavigator: EtaInfoNavigator
    ): TriRailNav

}