package com.tsgreenberg.station_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.wear.compose.material.MaterialTheme
import com.tsgreenberg.core.navigation.NavConstants
import com.tsgreenberg.core.navigation.TriRailNav
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.station_list.di.StationListNavigationQualifier
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StationListActivity : ComponentActivity() {

    @Inject
    @StationListNavigationQualifier
    lateinit var triRailNav: TriRailNavImplementor<NavHostController>

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition { false }
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                triRailNav.navController = navController
                NavHost(
                    navController = triRailNav.navController,
                    startDestination = NavConstants.STATION_LIST
                ) {
                    getStationList(triRailNav)
                }
            }
        }
    }
}


internal fun NavGraphBuilder.getStationList(triRailNav: TriRailNav) {
    navigation(
        startDestination = NavConstants.CHOOSE_STATION,
        route = NavConstants.STATION_LIST
    ) {
        composable(NavConstants.CHOOSE_STATION) {
            val viewModel: StationViewModel = hiltViewModel()
            ChooseStationNavigator(
                triRailNav = triRailNav,
                state = viewModel.state.value
            )
        }
    }
}