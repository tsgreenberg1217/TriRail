plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("kapt")
    id(Hilt.plugin)
    id(Sqldelight.plugin)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.tsgreenberg.trirailwearos"
        minSdk = 30
        targetSdk = 33
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
    Modules.run {
        implementation(project(stationList))
        implementation(project(etaInfo))
        implementation(project(uiComponents))
        implementation(project(core))
    }

    Compose.run {
        implementation(navigation)
        implementation(activity)
        implementation(uiTooling)
        implementation(compiler)
        implementation(material)
        debugImplementation(uiTest)
        implementation(wearMaterial)
        androidTestImplementation(junit)
        implementation(wearFoundation)
    }

    Hilt.apply {
        implementation(android)
        kapt(compiler)
        implementation(navigation)
    }

    implementation(Sqldelight.driver)

    AndroidX.run {
        implementation(splashScreen)
        androidTestImplementation(espresso)
    }


    testImplementation(Junit.test)
    androidTestImplementation(AndroidX.androidTest)


    Firebase.run {
        implementation(platform(bom))
        implementation(crashlytics)
        implementation(analytics)
        implementation(firestore)
    }


    Ktor.run {
        implementation(client)
        implementation(cio)
        implementation(contentNegotiation)
        implementation(serializationJson)
        implementation(logging)
    }
}
kapt {
    correctErrorTypes = true
}

sqldelight {
    database("TriRailDatabase") { // This will be the name of the generated database class.
        packageName = "com.tsgreenberg.trirailwearos"
        dependency(project(":station_list"))
//        dependency project(":eta_info")
    }
}