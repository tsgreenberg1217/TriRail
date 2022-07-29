package com.tsgreenberg.trirailwearos.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tsgreenberg.eta_info.remote_classes.EtaService
import com.tsgreenberg.eta_info.testing.MockEtaService
import com.tsgreenberg.station_list.StationsService
import com.tsgreenberg.trirailwearos.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    fun getStationsService(retrofit: Retrofit): StationsService =
        retrofit.create(StationsService::class.java)

    @Provides
    @Singleton
    fun getEtaService(retrofit: Retrofit): EtaService =
        retrofit.create(EtaService::class.java)
}