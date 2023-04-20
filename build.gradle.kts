plugins {
    kotlin("jvm") version "1.4.31"
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
        classpath("io.gitlab.arturbosch.detekt:detekt-cli:1.0.1")
        classpath("com.pinterest:ktlint:0.35.0")
    }
}

apply(from = "jacoco/project.gradle")

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
