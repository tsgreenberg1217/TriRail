package com.tsgreenberg.trirailwearos

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.tsgreenberg.core.Analytics.Analytics
import com.tsgreenberg.core.Analytics.AnalyticsData
import com.tsgreenberg.core.Analytics.Crashlytics
import com.tsgreenberg.core.Analytics.Firebase as FirebaseQualifier
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
@FirebaseQualifier
class CrashlyticsAnalytics @Inject constructor(crashlytics: FirebaseCrashlytics) :Analytics {
    override fun sendData(data: AnalyticsData) {
        TODO("Not yet implemented")
    }
}

@ActivityRetainedScoped
@Crashlytics
class FirebaseAnalytics @Inject constructor(firebase:Firebase):Analytics{
    override fun sendData(data: AnalyticsData) {
//        when(data){
//            is StationListAnalytics ->{
//                sendStationAnalytics(data)
//            }
//            is EtaAnalytucs -> {
//                sendEtaAnalytics
//            }
//        }
    }

}
