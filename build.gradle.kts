// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id ("com.android.application") version "7.3.0" apply false
    id ("com.android.library") version "7.3.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.7.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.squareup.sqldelight") version "1.5.3" apply false
    id("com.google.gms.google-services") version "4.3.14" apply false
    id("com.google.firebase.crashlytics") version "2.9.2" apply false
}


tasks.create<Delete>("cleanRP") {
    group = "rp"
    delete(
        fileTree("rp-out"),
        fileTree("rp-zip")
    )
}