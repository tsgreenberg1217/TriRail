package com.tsgreenberg.fm_eta.viewmodelTests

import android.util.Log
import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.ProgressBarState
import com.tsgreenberg.eta_info.mappers.TrainArrivalStateMapper
import com.tsgreenberg.fm_eta.models.ArrivalData
import com.tsgreenberg.fm_eta.models.EtaRefreshState
import com.tsgreenberg.fm_eta.models.TrainArrival
import com.tsgreenberg.fm_eta.remote_classes.GetEtaForStation
import com.tsgreenberg.fm_eta.ui.viewmodels.TrainArrivalViewModel
import com.tsgreenberg.fm_eta.utils.MainCoroutineRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*


@ExperimentalCoroutinesApi
class TrainArrivalViewModelShould {

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val mapper = mockk<TrainArrivalStateMapper>()
    private val getEtaForStation = mockk<GetEtaForStation>()

    // system under test
    private val viewModel: TrainArrivalViewModel = TrainArrivalViewModel(getEtaForStation, mapper)

    private val data = listOf<ArrivalData>()

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }

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
            Assert.assertEquals(ProgressBarState.Loading, etaProgressBarState)
        }

        advanceUntilIdle()

        viewModel.state.value.run {
            Assert.assertEquals(ProgressBarState.Idle, etaProgressBarState)
        }

    }

    @Test
    fun handlesDisableButton() = runTest {
        createSuccessCase()
        val now = Date().time

        viewModel.state.value.run {
            Assert.assertEquals(EtaRefreshState.Enabled, etaRefreshState)
        }

        viewModel.initialRefreshRequest(1, now)

        verify(exactly = 1) { getEtaForStation.execute(1) }

        viewModel.state.value.run {
            Assert.assertEquals(EtaRefreshState.Disabled(now), etaRefreshState)
        }

        // test left edge
        advanceTimeBy(59000)

        viewModel.state.value.run {
            Assert.assertEquals(EtaRefreshState.Disabled(now), etaRefreshState)
        }

        // test right edge
        advanceTimeBy(2000)

        viewModel.state.value.run {
            Assert.assertEquals(EtaRefreshState.Enabled, etaRefreshState)
        }

    }

    @Test
    fun blockRefreshBeforeTimeAllowed() = runTest {
        createSuccessCase()
        val now = Date().time
        viewModel.initialRefreshRequest(1, now)
        advanceTimeBy(59000)
        // this call should not trigger refresh
        viewModel.initialRefreshRequest(1, now)
        verify(exactly = 1) { getEtaForStation.execute(1) }
        Assert.assertEquals(EtaRefreshState.Disabled(now), viewModel.state.value.etaRefreshState)
    }

}