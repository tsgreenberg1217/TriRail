apply {
    from("$rootDir/android-library-build.gradle")
}

plugins{
    id("com.squareup.sqldelight")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
}

dependencies {
    "implementation"(project(":core"))
    "implementation"(project(":ui_components"))



    "implementation"(platform("com.google.firebase:firebase-bom:30.4.1"))
    "implementation"("com.google.firebase:firebase-firestore-ktx")
    "implementation" ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")
    "implementation"("com.google.accompanist:accompanist-pager:0.26.3-beta")

    "testImplementation" ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

}

sqldelight {
    database("TriRailDatabase") { // This will be the name of the generated database class.
        packageName = "com.tsgreenberg.eta_info"
    }
}