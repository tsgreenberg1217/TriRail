// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    Android.apply {
        id(application) version gradleVersion apply false
        id(library) version gradleVersion apply false
    }
    Kotlin.apply {
        id(androidPluginProject) version "1.7.10" apply false
    }
    Hilt.apply { id(plugin) version version apply false }
    Sqldelight.apply {
        id(plugin) version version apply false
    }
    Google.apply { id(servicesPlugin) version version apply false }
    Firebase.apply {
        id(crashlyticsPlugin) version crashlyticsVersion apply false
    }

}


tasks.create<Delete>("cleanRP") {
    group = "rp"
    delete(
        fileTree("rp-out"), fileTree("rp-zip")
    )
}