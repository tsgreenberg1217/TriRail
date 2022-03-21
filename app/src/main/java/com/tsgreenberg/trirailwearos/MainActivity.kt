package com.tsgreenberg.trirailwearos

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tsgreenberg.core.navigation.NavConstants
import com.tsgreenberg.core.navigation.TriRailNavRouteAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var triRailNav: TriRailNavImp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            triRailNav.setController(
                controller = navController
            )

            val navState =  navController.currentBackStackEntryAsState()
            Log.d("TRI RAIL", "current destination is ${navState.value?.destination?.route}")

            NavHost(
                navController = triRailNav.getController(),
                startDestination = NavConstants.STATION_LIST
            ) {
                with(triRailNav){
                    getStationList(this)
                    getStationDetail(this)
                }
            }
        }
    }
}