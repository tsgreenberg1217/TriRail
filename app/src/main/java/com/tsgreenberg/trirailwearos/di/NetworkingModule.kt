package com.tsgreenberg.trirailwearos.di

import com.tsgreenberg.eta_info.remote_classes.EtaService
import com.tsgreenberg.station_list.StationsService
import com.tsgreenberg.trirailwearos.EtaServiceImpl
import com.tsgreenberg.trirailwearos.StationServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun getStationsService(client: HttpClient): StationsService =
        StationServiceImpl(client)

    @Provides
    @Singleton
    fun getEtaService(client: HttpClient): EtaService = EtaServiceImpl(client)


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
