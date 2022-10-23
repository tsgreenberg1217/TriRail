pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "TriRailWearOs"
include(
    ":app",
    ":fm_stations",
    ":core",
    ":ui_components",
    ":fm_eta",
    ":fm_schedule"
)
