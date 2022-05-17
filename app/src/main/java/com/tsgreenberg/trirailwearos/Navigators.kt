package com.tsgreenberg.trirailwearos

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.core.view.ContentInfoCompat
import androidx.navigation.NavHostController
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.core.navigation.TriRailRootAction
import com.tsgreenberg.eta_info.ui.activity.EtaInfoActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class StationListNavigator @Inject constructor(
    @ApplicationContext val ctx: Context
) : TriRailNavImplementor<NavHostController>() {

    override lateinit var navController: NavHostController

    override fun navigate(routeAction: TriRailRootAction) {
        when (routeAction) {
            is TriRailRootAction.StationInfo -> {
                with(ctx) {
                    Intent(this, EtaInfoActivity::class.java).also {
                        it.putExtra(TriRailRootAction.StationInfo.intentKey, routeAction.station_id)
                        it.flags = FLAG_ACTIVITY_NEW_TASK
                        startActivity(it)
                    }
                }

            }
            else -> {}
        }

    }
}


class EtaInfoNavigator @Inject constructor(
    @ApplicationContext val ctx: Context
) : TriRailNavImplementor<NavHostController>() {

    override lateinit var navController: NavHostController

    override fun navigate(routeAction: TriRailRootAction) {
    }
}


