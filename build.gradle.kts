// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.compose.compiler) apply false
    kotlin("plugin.serialization") version "2.0.0" apply false
    kotlin("kapt") version "2.0.0" apply false
}