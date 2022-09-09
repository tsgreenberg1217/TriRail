package com.tsgreenberg.eta_info

import com.tsgreenberg.eta_info.mapperTests.EtaDtoMapperShould
import com.tsgreenberg.eta_info.mapperTests.TrainArrivalStateMapperShould
import com.tsgreenberg.eta_info.remote_classes.GetEtaForStation
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