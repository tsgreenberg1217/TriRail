apply {
    from("$rootDir/android-library-build.gradle")
}
plugins{
    Kotlin.apply { id(Kotlin.kotlinSerializationPlugin) version Kotlin.version }

    id(Sqldelight.plugin)
}
dependencies {
    Modules.run{
        "implementation"(project(core))
        "implementation"(project(uiComponents))
    }
    "implementation"(Kotlin.kotlinSerialization)
    "implementation"(Sqldelight.driver)
    "implementation"(AndroidX.splashScreen)
}

sqldelight {
    database("TriRailDatabase") { // This will be the name of the generated database class.
        packageName = "com.tsgreenberg.station_list"
    }
}