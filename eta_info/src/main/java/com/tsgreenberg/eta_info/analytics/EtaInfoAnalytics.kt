package com.tsgreenberg.eta_info.analytics

import com.tsgreenberg.core.Analytics.Analytics
import com.tsgreenberg.core.Analytics.AnalyticsData
import com.tsgreenberg.core.Analytics.Crashlytics
import com.tsgreenberg.core.Analytics.Firebase
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class EtaInfoAnalytics @Inject constructor(
    @Firebase val firebaseAnalytics: Analytics, @Crashlytics val crashlytics: Analytics
) {
    fun trackSwipe(data: AnalyticsData) {
        firebaseAnalytics.sendData(data)
    }

    fun trackUser(data: AnalyticsData) {
        crashlytics.sendData(data)
    }
}