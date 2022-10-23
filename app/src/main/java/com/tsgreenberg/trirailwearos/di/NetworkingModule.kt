package com.tsgreenberg.trirailwearos.di

import com.google.firebase.firestore.FirebaseFirestore
import com.tsgreenberg.eta_info.remote_classes.EtaService
import com.tsgreenberg.schedule.TrainScheduleService
import com.tsgreenberg.station_list.StationsService
import com.tsgreenberg.trirailwearos.EtaServiceImpl
import com.tsgreenberg.trirailwearos.StationServiceImpl
import com.tsgreenberg.trirailwearos.TrainScheduleServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {


    @Provides
    @Singleton
    fun getHttpClient(): HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
            filter { request ->
                request.url.host.contains("ktor.io")
            }
        }
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
object NetworkImplModule {
    @Provides
    @ActivityRetainedScoped
    fun getStationsService(client: HttpClient): StationsService =
        StationServiceImpl(client)

    @Provides
    @ActivityRetainedScoped
    fun getEtaService(client: HttpClient): EtaService = EtaServiceImpl(client)

    @Provides
    @ActivityRetainedScoped
    fun providesTrainScheduleService(
        fireStore: FirebaseFirestore
    ): TrainScheduleService = TrainScheduleServiceImpl(fireStore)
}
