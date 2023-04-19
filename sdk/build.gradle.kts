plugins {
    id("com.android.library")
    id("kotlin-android")
    id("jacoco")
}

android {
    compileSdkVersion(31)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //consumerProguardFiles = "consumer-rules.pro"
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
    androidTestImplementation(Tests.junit)
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