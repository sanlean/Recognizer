import org.gradle.api.tasks.Delete

plugins {
    kotlin("jvm") version "1.4.31"
}

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
