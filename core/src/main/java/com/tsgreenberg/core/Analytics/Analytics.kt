package com.tsgreenberg.core.Analytics

import javax.inject.Qualifier

interface Analytics {
    fun sendData(data:AnalyticsData)
}

abstract class AnalyticsData

// qualifiers

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Crashlytics

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Firebase
