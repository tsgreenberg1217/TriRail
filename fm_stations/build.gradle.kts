plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin(Kotlin.kapt)
    id(Hilt.plugin)
    Kotlin.apply { id(kotlinSerializationPlugin) version version }
    id(Sqldelight.plugin)
}



android {
    compileSdk = 34
    namespace = "com.tsgreenberg.station_list"

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Kotlin.complilerVersion
    }

}



dependencies {
    Modules.run {
        implementation(project(core))
        implementation(project(uiComponents))
    }
    implementation(Kotlin.kotlinSerialization)
    implementation(Sqldelight.driver)
    implementation(AndroidX.splashScreen)


    kaptAndroidTest(Hilt.compilerTest)
    kapt(Hilt.compiler)
    kaptTest(Hilt.compilerTest) // For instrumented tests.


    implementation(Hilt.android)
    implementation(Hilt.navigation)
    androidTestImplementation(Hilt.testing) // ...with Kotlin.

    implementation(Compose.activity)
    implementation(Compose.uiTooling)
    implementation(Compose.compiler)
    implementation(Compose.navigation)
    implementation(Compose.wearMaterial)
    implementation(Compose.wearFoundation)
    debugImplementation(Compose.uiTest)
    androidTestImplementation(Compose.junit)

    implementation(Coroutines.android)
    testImplementation(Coroutines.androidTest)

    testImplementation(Junit.test)

    implementation(AndroidX.navigation)
    androidTestImplementation(AndroidX.androidTest)
    androidTestImplementation(AndroidX.espresso)
    androidTestImplementation(AndroidX.testCore)
    androidTestImplementation(AndroidX.testCoreKtx)
    androidTestImplementation(AndroidX.testRunner)
    androidTestImplementation(AndroidX.testRules)
    androidTestUtil(AndroidX.testOrchestrator)

    testImplementation(Mockk.mockk)
    androidTestImplementation(Mockk.mockkAndroid)
}

sqldelight {
    database("TriRailDatabase") { // This will be the name of the generated database class.
        packageName = "com.tsgreenberg.station_list"
    }
}