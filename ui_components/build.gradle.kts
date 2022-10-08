apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {

    "implementation"(project(":core"))
    "implementation"("com.google.accompanist:accompanist-pager:0.24.13-rc")

}