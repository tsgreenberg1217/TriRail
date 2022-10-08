apply {
    from("$rootDir/android-library-build.gradle")
}
plugins{
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
    id(Sqldelight.plugin)
}
dependencies {
    Modules.run{
        "implementation"(project(core))
        "implementation"(project(uiComponents))
    }
    "implementation"(Ktor.kotlinSerialization)
    "implementation"(Sqldelight.driver)
    "implementation"(AndroidX.splashScreen)
}

sqldelight {
    database("TriRailDatabase") { // This will be the name of the generated database class.
        packageName = "com.tsgreenberg.station_list"
    }
}