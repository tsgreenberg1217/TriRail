import org.gradle.kotlin.dsl.`kotlin-dsl`


plugins {
    `kotlin-dsl`
}

buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
//        classpath("com.android.tools.build:gradle:7.2.2")
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
//    implementation("com.android.tools.build:gradle:3.4.3")
//    implementation("com.android.tools.build:gradle-api:3.4.3")
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.10")
//    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")
}
