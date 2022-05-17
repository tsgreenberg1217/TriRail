package com.tsgreenberg.eta_info.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.compiler.plugins.kotlin.EmptyFunctionMetrics.composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.wear.compose.material.MaterialTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tsgreenberg.core.navigation.NavConstants
import com.tsgreenberg.core.navigation.TriRailNavImplementor
import com.tsgreenberg.core.navigation.TriRailRootAction
import com.tsgreenberg.eta_info.di.EtaInfoNavigationQualifier
import com.tsgreenberg.eta_info.models.EtaInfoViewModelCache
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class EtaInfoActivity : ComponentActivity() {

    @Inject
    @EtaInfoNavigationQualifier
    lateinit var triRailNav: TriRailNavImplementor<NavHostController>

    @Inject
    lateinit var viewModelCache: EtaInfoViewModelCache

    var stationId: Int? = null


    data class JsonSchedule(
        val station_id: Int,
        val train_id: Int,
        val direction: String,
        val time: String,
        val is_weekday: Int
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                triRailNav.navController = navController
                NavHost(
                    navController = navController,
                    startDestination = NavConstants.ETA
                ) {
                    composable(
                        route = NavConstants.ETA,
                    ) {
                        viewModelCache.stationId =
                            intent.extras?.getInt(TriRailRootAction.StationInfo.intentKey) ?: -1
                        val viewModel: StationDetailViewModel = hiltViewModel()

                        EtaScreen(
                            viewModel.state.value,
                            refresh = { viewModel.refresh(id) },
                            goToTrainSchedule = {
                                viewModelCache.trainDirection = it
                                navController.navigate("${NavConstants.STATION_INFO}/$it")
                            }
                        )
                    }

                    composable(
                        NavConstants.STATION_INFO_ROUTE,
                        arguments = listOf(navArgument("station_id") {
                            type = NavType.StringType
                            defaultValue = "S"
                        })
                    ) {
                        val viewModel: TrainScheduleViewModel = hiltViewModel()
                        TrainScheduleScreen(
                            state = viewModel.state.value
                        )

                    }
                }

            }
        }
    }
}


//composable{
//            val entry = remember{
//                triRailNav.navController.getBackStackEntry(NavConstants.STATION_INFO_ROUTE)
//            }
//            val id = entry.arguments?.getString("station_id") ?: "0"
//}