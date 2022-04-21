package com.tsgreenberg.trirailwearos

import android.content.Context
import android.content.Intent
import androidx.navigation.NavHostController
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.core.navigation.TriRailRootAction
import com.tsgreenberg.eta_info.EtaInfoActivity
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject


class StationListNavigator @Inject constructor(
    @ActivityContext val ctx: Context
) : TriRailNavImplementor<NavHostController>() {

    override lateinit var navController: NavHostController

    override fun navigate(routeAction: TriRailRootAction) {
        when (routeAction) {
            is TriRailRootAction.StationInfo -> {
                with(ctx) {
                    Intent(this, EtaInfoActivity::class.java).also {
                        it.putExtra(TriRailRootAction.StationInfo.intentKey, routeAction.station_id)
                        startActivity(it)
                    }
                }

            }
            else -> {}
        }

    }
}


class EtaInfoNavigator @Inject constructor(
    @ActivityContext val ctx: Context
) : TriRailNavImplementor<NavHostController>() {

    override lateinit var navController: NavHostController

    override fun navigate(routeAction: TriRailRootAction) {
    }
}


