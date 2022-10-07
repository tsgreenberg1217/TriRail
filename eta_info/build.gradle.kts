plugins{
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.squareup.sqldelight")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 30
        targetSdk = 32
        testInstrumentationRunner = "com.tsgreenberg.eta_info.CustomTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
//        useIR = true
    }


    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }

    packagingOptions {
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE-notice.md")
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }



}

dependencies {

    implementation("androidx.navigation:navigation-compose:2.5.2")
    implementation("androidx.activity:activity-compose:1.6.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
    implementation("androidx.compose.compiler:compiler:1.3.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-compiler:2.44")
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    implementation("com.squareup.sqldelight:android-driver:1.5.3")
//    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("androidx.wear.compose:compose-material:1.1.0-alpha07")
    implementation("androidx.wear.compose:compose-foundation:1.1.0-alpha07")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.5.2")
    androidTestImplementation("androidx.navigation:navigation-testing:2.5.2")
    implementation("androidx.navigation:navigation-compose:2.5.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kaptTest("com.google.dagger:hilt-android-compiler:2.43.2")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.43.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.2.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.2.1")
    androidTestImplementation("androidx.test:core:1.4.0")
    androidTestImplementation("androidx.test:core-ktx:1.4.0")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test:rules:1.4.0")
    androidTestUtil("androidx.test:orchestrator:1.4.1")
    testImplementation("io.mockk:mockk:1.12.7")
    androidTestImplementation("io.mockk:mockk-android:1.12.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")
    implementation(project(":core"))
    implementation(project(":ui_components"))



    implementation(platform("com.google.firebase:firebase-bom:30.4.1"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")
    implementation("com.google.accompanist:accompanist-pager:0.26.3-beta")

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

}

sqldelight {
    database("TriRailDatabase") { // This will be the name of the generated database class.
        packageName = "com.tsgreenberg.eta_info"
    }
}