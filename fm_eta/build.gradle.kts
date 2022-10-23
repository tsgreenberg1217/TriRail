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

    "implementation"(Accompanist.pager)
    "implementation"(Accompanist.pagerIndicators)
    "implementation"(Kotlin.kotlinSerialization)

}

sqldelight {
    database("TriRailDatabase") { // This will be the name of the generated database class.
        packageName = "com.tsgreenberg.fm_eta"
    }
}