package com.tsgreenberg.trirailwearos.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tsgreenberg.eta_info.remote_classes.EtaService
import com.tsgreenberg.station_list.StationsService
import com.tsgreenberg.trirailwearos.BuildConfig
import com.tsgreenberg.trirailwearos.EtaServiceKtorImpl
import com.tsgreenberg.trirailwearos.StationServiceKtorImpl
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
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {
    @Provides
    fun getGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    fun getInterceptor(): Interceptor = Interceptor {
        it.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .build().let { newReq -> it.proceed(newReq) }
    }


    @Provides
    fun getClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
//            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }.build()
    }

    @Provides
    fun getRetrofit(client: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder().apply {
        baseUrl(BuildConfig.API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))

    }.build()


    @Provides
    @Singleton
    fun getStationsService(client: HttpClient): StationsService =
        StationServiceKtorImpl(client)

    @Provides
    @Singleton
    fun getEtaService(client: HttpClient): EtaService = EtaServiceKtorImpl(client)


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


    //    @Provides
//    @Singleton
//    fun getEtaService(retrofit: Retrofit): EtaService =
//        retrofit.create(EtaService::class.java)


//
//    @Provides
//    @Singleton
//    fun getStationsService(retrofit: Retrofit): StationsService =
//        retrofit.create(StationsService::class.java)

}
