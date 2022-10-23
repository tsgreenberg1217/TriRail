package com.tsgreenberg.fm_eta

import com.tsgreenberg.fm_eta.mapperTests.EtaDtoMapperShould
import com.tsgreenberg.fm_eta.mapperTests.TrainArrivalStateMapperShould
import com.tsgreenberg.fm_eta.remote_classes.GetEtaForStation
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(Suite::class)
@Suite.SuiteClasses(
    EtaDtoMapperShould::class,
    TrainArrivalStateMapperShould::class,
    TrainArrivalStateMapperShould::class
)
class EtaUnitTestSuite