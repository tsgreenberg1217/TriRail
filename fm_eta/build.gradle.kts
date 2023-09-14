plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id(Sqldelight.plugin)
    id(Hilt.plugin)
    kotlin(Kotlin.kapt)
    Kotlin.apply { id(kotlinSerializationPlugin) version version }

}
android {
    namespace = "com.tsgreenberg.fm_eta"
    compileSdk = 34

    defaultConfig {
        minSdk = 30
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
//        useIR = true
    }


    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Kotlin.complilerVersion
    }

    packagingOptions {
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE-notice.md")
    }

//    testOptions {
//        unitTests {
//            includeAndroidResources = true
//        }
//    }



}

dependencies {


    Modules.run {
        implementation(project(core))
        implementation(project(uiComponents))
    }

    implementation(Sqldelight.driver)

    implementation(Accompanist.pager)
    implementation(Accompanist.pagerIndicators)
    implementation(Kotlin.kotlinSerialization)






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
        packageName = "com.tsgreenberg.fm_eta"
    }
}