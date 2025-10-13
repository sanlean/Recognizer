plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.kotlin.android")
}

apply(from = "../jacoco/modules.gradle")

android {
    compileSdk = 35
    buildToolsVersion = "35.0.0"

    defaultConfig {
        applicationId = "com.example.testapp"
        minSdk = 21
        versionCode = 3
        versionName = "1.0.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
    }
    buildFeatures {
        viewBinding = true
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
    namespace = "com.example.testapp"
}

dependencies {
    implementation(project(mapOf("path" to ":sdk")))
    implementation(Support.appcompat)
    implementation(Support.constraintlayout)
    implementation(Support.corektx)
    implementation(Support.cardview)
    implementation(KotlinLibs.stdlib)

    implementation(Camera.camera2)
    implementation(Camera.cameraLifecycle)
    implementation(Camera.cameraView)

    implementation(MachineLearningGoogleKit.textRecognition)
    implementation(MachineLearningGoogleKit.faceDetection)

    testImplementation(Tests.androidxJUnit)
    androidTestImplementation(Tests.androidxJUnit)
    androidTestImplementation(Tests.espresso)
}
