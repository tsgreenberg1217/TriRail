package com.tsgreenberg.eta_info.viewmodelTests

import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.ProgressBarState
import com.tsgreenberg.eta_info.mappers.TrainArrivalStateMapper
import com.tsgreenberg.eta_info.models.ArrivalData
import com.tsgreenberg.eta_info.models.EtaRefreshState
import com.tsgreenberg.eta_info.models.TrainArrival
import com.tsgreenberg.eta_info.models.TrainInfoState
import com.tsgreenberg.eta_info.remote_classes.GetEtaForStation
import com.tsgreenberg.eta_info.ui.viewmodels.TrainArrivalViewModel
import com.tsgreenberg.eta_info.utils.MainCoroutineRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class TrainArrivalViewModelShould {

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val mapper = mockk<TrainArrivalStateMapper>()
    private val getEtaForStation = mockk<GetEtaForStation>()

    val viewModel : TrainArrivalViewModel = TrainArrivalViewModel(getEtaForStation, mapper)

    val data = listOf<ArrivalData>()

    private fun createSuccessCase() {
        val state = mapOf<String, TrainArrival>(
            "North" to TrainArrival.EstimatedArrival(
                info = 2,
                trainId = "P619",
                status = "On Time",
                statusColor = "",
                trackNumber = 2,
            ),

            "South" to TrainArrival.EstimatedArrival(
                info = 2,
                trainId = "P612",
                status = "On Time",
                statusColor = "",
                trackNumber = 1,
            )
        )

        every { getEtaForStation.execute(any()) } returns flow {
            delay(3000)
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            delay(3000)
            emit(DataState.Success(data))
            delay(3000)
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }


        every { mapper.invoke(any()) } returns state
    }

    @Test
    fun successCallsMapper() = runTest {
        createSuccessCase()
        viewModel.getEstTrainArrivals(1)
        advanceUntilIdle()
        verify(exactly = 1) { mapper.invoke(any()) }
    }


    @Test
    fun manageLoadingSpinner() = runTest {
        createSuccessCase()
        viewModel.getEstTrainArrivals(1)
        viewModel.state.value.run {
            etaProgressBarState is ProgressBarState.Loading
        }

        advanceTimeBy(3000)

        viewModel.state.value.run {
            etaProgressBarState is ProgressBarState.Idle
        }

    }

}