package com.tsgreenberg.trirailwearos

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.core.view.ContentInfoCompat
import androidx.navigation.NavHostController
import com.tsgreenberg.core.navigation.TriRailNav
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.core.navigation.TriRailRootAction
import com.tsgreenberg.eta_info.navigation.EtaNavigationAction
import com.tsgreenberg.eta_info.navigation.EtaNavigationAction.Companion.stationId
import com.tsgreenberg.eta_info.ui.activity.EtaInfoActivity
import com.tsgreenberg.schedule.ScheduleActivity
import com.tsgreenberg.station_list.StationListNavigation
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class StationListNavigator @Inject constructor(
    @ApplicationContext val ctx: Context
) : TriRailNav {

    override fun navigate(action: TriRailRootAction) {
        when (action) {
            is StationListNavigation.StationEtaInfo -> {
                with(ctx) {
                    Intent(this, EtaInfoActivity::class.java).also {
                        it.putExtra(
                            StationListNavigation.StationEtaInfo.intentKey,
                            action.station_id
                        )
                        it.putExtra(
                            StationListNavigation.StationEtaInfo.intentKeyName,
                            action.shortName
                        )
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
) : TriRailNav {

    override fun navigate(action: TriRailRootAction) {
        when (action) {
            is EtaNavigationAction.GoToStationSchedule -> {
                with(ctx) {
                    Intent(this, ScheduleActivity::class.java).also {
                        it.putExtra(
                            stationId,
                            action.id
                        )
                        it.flags = FLAG_ACTIVITY_NEW_TASK
                        startActivity(it)
                    }
                }

            }
        }
    }
}


