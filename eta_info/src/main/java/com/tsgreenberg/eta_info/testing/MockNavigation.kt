package com.tsgreenberg.eta_info.testing

import android.content.Context
import androidx.navigation.NavHostController
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.core.navigation.TriRailRootAction
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MockNavigation @Inject constructor(
    @ApplicationContext val ctx: Context
) : TriRailNavImplementor<NavHostController>() {

    override lateinit var navController: NavHostController

    override fun navigate(routeAction: TriRailRootAction) {
    }
}
