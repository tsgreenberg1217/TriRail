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
    ":station_list",
    ":core",
    ":ui_components",
    ":eta_info",
    ":schedule"
)
