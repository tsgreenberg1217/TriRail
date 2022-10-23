package com.tsgreenberg.eta_info.navigation

import com.tsgreenberg.core.navigation.TriRailRootAction

sealed class EtaNavigationAction:TriRailRootAction(){
    data class GoToStationSchedule(val id:Int): EtaNavigationAction()

    companion object {
        const val stationId = "stationId"
    }
}