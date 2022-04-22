package com.tsgreenberg.eta_info

import com.tsgreenberg.core.DataState
import com.tsgreenberg.core.ProgressBarState
import com.tsgreenberg.eta_info.remote_classes.GetEtaForStation
import com.tsgreenberg.eta_info.testing.MockEtaService
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class EtaInfoTests {
    // system under test
    private lateinit var getEtaForStation : GetEtaForStation

    @Test
    fun getEta_Success() = runBlocking {
        getEtaForStation = GetEtaForStation(MockEtaService())
        val emissions = getEtaForStation.execute(1).toList()
        assert(emissions[0] is DataState.Loading)
        assert((emissions[0] as DataState.Loading).progressBarState == ProgressBarState.Loading)
        assert(emissions[1] is DataState.Success)
        assert(emissions[2] is DataState.Loading)
        assert((emissions[2] as DataState.Loading).progressBarState == ProgressBarState.Idle)
    }
}
