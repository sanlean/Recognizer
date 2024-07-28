plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.android")
}

apply(from = "../jacoco/modules.gradle")

android {
    compileSdk = 35
    buildToolsVersion = "35.0.0"

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles.add(File("consumer-rules.pro"))
    }

    buildTypes {
        debug {
            enableAndroidTestCoverage = false
            enableUnitTestCoverage = true
        }
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
    sourceSets {
        getByName("main") {
            java.setSrcDirs(listOf("src/main/kotlin"))
        }
        getByName("test") {
            java.setSrcDirs(listOf("src/test/kotlin"))
        }
        getByName("androidTest") {
            java.setSrcDirs(listOf("src/androidTest/kotlin"))
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }
    packagingOptions {
        resources.excludes.add("win32-x86/attach_hotspot_windows.dll")
        resources.excludes.add("win32-x86-64/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/*")
        resources.excludes.add("META-INF/*/*")
    }
    namespace = "com.example.sdk"
}

dependencies {
    implementation(KotlinLibs.stdlib)
    implementation(Support.appcompat)
    implementation(Support.corektx)
    implementation(Design.material)

    implementation(Lifecycle.viewModel)
    implementation(Lifecycle.viewModelKtx)
    implementation(Lifecycle.liveData)
    implementation(Lifecycle.activityKtx)

    implementation(Camera.camera2)
    implementation(Camera.cameraLifecycle)
    implementation(Camera.cameraView)

    implementation(MachineLearningGoogleKit.textRecognition)
    implementation(MachineLearningGoogleKit.faceDetection)

    testImplementation(Tests.junit)
    testImplementation(Tests.androidxJUnit)
    testImplementation(Tests.mockk)
    testImplementation(Tests.mockito)
    testImplementation(Tests.truth)
    testImplementation(Tests.androidCore)
    testImplementation(Tests.coreKtx)
    testImplementation(Tests.runner)
    testImplementation(Tests.rules)
    testImplementation(Tests.robolectric)
    testImplementation(Tests.coroutines)
    testImplementation(Tests.espresso)
    androidTestImplementation(Tests.androidxJUnit)
    androidTestImplementation(Tests.espresso)
    androidTestImplementation(Tests.mockk)
    androidTestImplementation(Tests.mockito)
    androidTestImplementation(Tests.truth)
    androidTestImplementation(Tests.androidCore)
    androidTestImplementation(Tests.coreKtx)
    androidTestImplementation(Tests.runner)
    androidTestImplementation(Tests.rules)
    androidTestImplementation(Tests.robolectric)
    androidTestImplementation(Tests.coroutines)
}