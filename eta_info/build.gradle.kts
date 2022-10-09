apply {
    from("$rootDir/android-library-build.gradle")
}

plugins{
    id(Sqldelight.plugin)
    Kotlin.apply { id(kotlinSerializationPlugin) version version }

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
    "implementation"(Kotlin.kotlinSerialization)

}

sqldelight {
    database("TriRailDatabase") { // This will be the name of the generated database class.
        packageName = "com.tsgreenberg.eta_info"
    }
}