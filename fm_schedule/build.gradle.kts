apply {
    from("$rootDir/android-library-build.gradle")
}

plugins{
    Kotlin.apply { id(kotlinSerializationPlugin) version version }

}

dependencies {
    Modules.run {

        "implementation"(project(core))
        "implementation"(project(uiComponents))
    }

    "implementation"(Accompanist.pager)
    "implementation"(Accompanist.pagerIndicators)
    "implementation"(Kotlin.kotlinSerialization)

}