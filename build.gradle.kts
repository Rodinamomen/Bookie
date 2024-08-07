

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
   repositories {
       google()
       mavenCentral()
    }
    dependencies {
        classpath ("com.google.gms:google-services:4.3.10")  // Add this line
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
  }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
    id ("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false
    id("org.jetbrains.kotlin.kapt") version "1.8.22" apply false


}
