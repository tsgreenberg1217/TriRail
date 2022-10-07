// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.3.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.3")
        classpath("com.google.gms:google-services:4.3.14")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.2")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

tasks.create<Delete>("cleanRP") {
    group = "rp"
    delete(
        fileTree("rp-out"),
        fileTree("rp-zip")
    )
}