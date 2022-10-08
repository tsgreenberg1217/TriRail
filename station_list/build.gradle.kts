apply {
    from("$rootDir/android-library-build.gradle")
}
plugins{
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
    id("com.squareup.sqldelight")
}
dependencies {
    "implementation"(project(":core"))
    "implementation"(project(":ui_components"))
    "implementation"("androidx.core:core-splashscreen:1.0.0-beta01")
}

sqldelight {
    database("TriRailDatabase") { // This will be the name of the generated database class.
        packageName = "com.tsgreenberg.station_list"
    }
}