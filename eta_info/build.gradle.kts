apply {
    from("$rootDir/android-library-build.gradle")
}

plugins{
    id(Sqldelight.plugin)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
}

dependencies {
    Modules.run {

        "implementation"(project(core))
        "implementation"(project(uiComponents))
    }

    "implementation"(Sqldelight.driver)

    Firebase.run {
        "implementation"(platform(bom))
        "implementation"(firestore)
    }
    Coroutines.apply {
        "implementation"(playServices)
        "testImplementation"(androidTest)
    }
    "implementation"(Accompanist.pager)
    "implementation"(Ktor.kotlinSerialization)

}

sqldelight {
    database("TriRailDatabase") { // This will be the name of the generated database class.
        packageName = "com.tsgreenberg.eta_info"
    }
}