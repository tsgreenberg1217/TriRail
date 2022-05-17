package com.tsgreenberg.trirailwearos.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tsgreenberg.trirailwearos.TriRailDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.tsgreenberg.etainfo.TrainSchedule
import com.tsgreenberg.etainfo.TrainScheduleQueries
import com.tsgreenberg.stationlist.StationQueries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providesDatabaseDriver(@ApplicationContext context: Context): SqlDriver {
        return AndroidSqliteDriver(TriRailDatabase.Schema, context, "trirail_database.db")
    }

    @Provides
    @Singleton
    fun providesDatabase(driver: SqlDriver): TriRailDatabase {
        return TriRailDatabase(driver)
    }

    @Provides
    @Singleton
    fun providesStationListQueries(db: TriRailDatabase): StationQueries = db.stationQueries

    @Provides
    @Singleton
    fun providesSchedule(db: TriRailDatabase): TrainScheduleQueries = db.trainScheduleQueries

    @Provides
    @Singleton
    fun providesFireStore(): FirebaseFirestore = Firebase.firestore


}