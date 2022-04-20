package com.tsgreenberg.trirailwearos

import android.content.Context
import android.content.Intent
import androidx.navigation.NavHostController
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.core.navigation.TriRailRootAction
import com.tsgreenberg.station_info.EtaInfoActivity
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class StationListNavigator @Inject constructor(
    @ActivityContext val ctx: Context
) : TriRailNavImplementor<NavHostController>() {

    override lateinit var navController: NavHostController

    override fun navigate(routeAction: TriRailRootAction) {
        when (routeAction) {
            is TriRailRootAction.StationInfo -> {
                with(ctx) {
                    Intent(this, EtaInfoActivity::class.java).also { startActivity(it) }
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


