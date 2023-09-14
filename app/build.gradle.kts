plugins {
    id(Android.application)
    Kotlin.apply {
        id(androidPlugin)
        kotlin(kapt)
    }
    id(Hilt.plugin)
    id(Sqldelight.plugin)
    id(Google.servicesPlugin)
    id(Firebase.crashlyticsPlugin)
    Kotlin.apply { id(kotlinSerializationPlugin) version version }

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

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    namespace = "com.tsgreenberg.trirailwearos"

}

dependencies {
    Modules.run {
        implementation(project(stationList))
        implementation(project(etaInfo))
        implementation(project(uiComponents))
        implementation(project(core))
        implementation(project(schedule))
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

    Coroutines.apply {
        implementation(playServices)
        testImplementation(androidTest)
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
        dependency(project(Modules.stationList))
    }
}