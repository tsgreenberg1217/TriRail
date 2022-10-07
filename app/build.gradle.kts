plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.squareup.sqldelight")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.tsgreenberg.trirailwearos"
        minSdk = 30
        targetSdk = 32
        versionCode = 3
        versionName = "1.1.0"
        buildConfigField("String", "API_URL", "\"https://trirailpublic.etaspot.net\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
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

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

}

dependencies {
    implementation(project(":station_list"))
    implementation(project(":eta_info"))
    implementation(project(":ui_components"))
    implementation(project(":core"))


    implementation("androidx.navigation:navigation-compose:2.5.2")
    implementation("androidx.activity:activity-compose:1.6.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
    implementation("androidx.compose.compiler:compiler:1.3.2")
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-compiler:2.44")
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("com.squareup.sqldelight:android-driver:1.5.3")
    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation("androidx.compose.material:material:1.3.0-beta03")


    debugImplementation("androidx.compose.ui:ui-test-manifest:1.2.1")

    // Compose for Wear OS Dependencies
    // NOTE: DO NOT INCLUDE a dependency on androidx.compose.material:material.
    // androidx.wear.compose:compose-material is designed as a replacement not an addition to
    // androidx.compose.material:material. If there are features from that you feel are missing from
    // androidx.wear.compose:compose-material please raise a bug to let us know:
    // https://issuetracker.google.com/issues/new?component=1077552&template=1598429&pli=1
    implementation("androidx.wear.compose:compose-material:1.1.0-alpha07")

    // Foundation is additive, so you can use the mobile version in your Wear OS app.
    implementation("androidx.wear.compose:compose-foundation:1.1.0-alpha07")

    // If you are using Compose Navigation, use the Wear OS version (NOT the
    // androidx.navigation:navigation-compose version), that is, uncomment the line below.
    //( implementation "androidx.wear.compose:compose-navigation:1.1.0-alpha07")

    // Testing
    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation "androidx.test.ext:junit:1.1.3"
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.3")

    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.2.1")


    // Java language implementation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")

    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")

    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.5.2")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:2.5.2")

    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:2.5.2")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")


    implementation(platform("com.google.firebase:firebase-bom:30.4.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-crashlytics")

    implementation("io.ktor:ktor-client-core:2.1.2")
    implementation("io.ktor:ktor-client-cio:2.1.2")
    implementation("io.ktor:ktor-client-content-negotiation:2.1.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.2")
    implementation("io.ktor:ktor-client-logging:2.1.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")


}

sqldelight {
    database("TriRailDatabase") { // This will be the name of the generated database class.
        packageName = "com.tsgreenberg.trirailwearos"
        dependency(project(":station_list"))
//        dependency project(":eta_info")
    }
}