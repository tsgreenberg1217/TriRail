package com.tsgreenberg.eta_info.EtaScreenTesting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.tsgreenberg.eta_info.models.TrainArrival
import com.tsgreenberg.eta_info.testing.TestingTags
import com.tsgreenberg.eta_info.ui.screens.NORTHBOUND_ETA
import com.tsgreenberg.eta_info.ui.screens.ShowRouteInfo
import com.tsgreenberg.ui_components.toEtaString
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RouteInfoTests {


    @get:Rule
    val composeRule = createComposeRule()


    fun setup(title:String,trainArrival: TrainArrival){
        composeRule.run {
            setContent {
                Box(Modifier.background(Color.Black)) {
                    ShowRouteInfo(
                        title,
                        trainArrival
                    ) {}
                }
            }
        }
    }
    @Test
    fun testEstimatedArrival() {
        val title = NORTHBOUND_ETA
        val trainArrival = TrainArrival.EstimatedArrival(
            info = 33,
            trainId = "7",
            status = "On Time",
            statusColor = "#FFFF0000",
            trackNumber = 3
        )

        setup(title, trainArrival)
        composeRule.run {
            onNodeWithTag(TestingTags.TRAIN_ARRIVAL).assertExists()
            onNodeWithTag(TestingTags.TRAIN_END_OF_LINE).assertDoesNotExist()
            onNodeWithTag(TestingTags.TRAIN_NO_INFO).assertDoesNotExist()

            onNodeWithTag(TestingTags.TRAIN_ARRIVAL_TRACK)
                .onChildren()
                .assertAny(hasText("Track #${trainArrival.trackNumber}"))

            onNodeWithTag(TestingTags.TRAIN_ARRIVAL_INFO)
                .assertTextEquals(trainArrival.info.toEtaString())
        }

    }


    @Test
    fun testNoInfo() {
        val title = NORTHBOUND_ETA
        val trainArrival = TrainArrival.NoInformation
        setup(title, trainArrival)
        composeRule.run {
            onNodeWithTag(TestingTags.TRAIN_ARRIVAL).assertDoesNotExist()
            onNodeWithTag(TestingTags.TRAIN_END_OF_LINE).assertDoesNotExist()
            onNodeWithTag(TestingTags.TRAIN_NO_INFO).assertExists()
        }
    }



    @Test
    fun testEndOfLine() {
        val title = NORTHBOUND_ETA
        val trainArrival = TrainArrival.EndOfLine
        setup(title, trainArrival)
        composeRule.run {
            onNodeWithTag(TestingTags.TRAIN_ARRIVAL).assertDoesNotExist()
            onNodeWithTag(TestingTags.TRAIN_END_OF_LINE).assertExists()
            onNodeWithTag(TestingTags.TRAIN_NO_INFO).assertDoesNotExist()
        }
    }



}