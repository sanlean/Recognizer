class Deps{
    public static final support = [
            appcompat: "androidx.appcompat:appcompat:$Versions.appcompat",
            constraintlayout: "androidx.constraintlayout:constraintlayout:$Versions.constraintlayout",
            recyclerview: "androidx.recyclerview:recyclerview:$Versions.recyclerview",
            swiperefreshlayout: "androidx.swiperefreshlayout:swiperefreshlayout:$Versions.swiperefresh",
            cardview: "androidx.cardview:cardview:$Versions.cardview",
            corektx: "androidx.core:core-ktx:$Versions.corektx",
            collectionsKtx: "androidx.collection:collection-ktx:$Versions.collectionsktx",
            fragmentKtx: "androidx.fragment:fragment-ktx:$Versions.fragmentKtx",
            lifecycleKtx: "androidx.lifecycle:lifecycle-runtime-ktx:$Versions.lifecycleKtx",
            lifecycleExtensions: "androidx.lifecycle:lifecycle-extensions:$Versions.lifecycleExtensions",
            percentlayout: "androidx.percentlayout:percentlayout:$Versions.percentlayout",
            multidex: "androidx.multidex:multidex:$Versions.multidex"
    ]

    public static final lifecycle = [
            viewModel: "androidx.lifecycle:lifecycle-viewmodel:$Versions.lifecycle",
            viewModelKtx: "androidx.lifecycle:lifecycle-viewmodel-ktx:$Versions.lifecycle",
            liveData: "androidx.lifecycle:lifecycle-livedata:$Versions.lifecycle",
            activityKtx: "androidx.activity:activity-ktx:$Versions.activityKtx"
    ]

    public static final design = [
            material: "com.google.android.material:material:$Versions.material",
            paging: "androidx.paging:paging-runtime:$Versions.paging",
            pagingRxjava: "androidx.paging:paging-rxjava2:$Versions.paging"
    ]

    public static final tests = [
            junit: "androidx.test.ext:junit:$Versions.junit",
            robolectric: "org.robolectric:robolectric:$Versions.robolectric",
            truth: "androidx.test.ext:truth:$Versions.truth",
            androidCore: "androidx.arch.core:core-testing:$Versions.coreTesting",
            coreKtx: "androidx.test:core-ktx:$Versions.coreKtxTest",
            mockk: "io.mockk:mockk:$Versions.mockk",
            mockito: "org.mockito:mockito-core:$Versions.mockito",
            runner: "androidx.test:runner:$Versions.runner",
            rules: "androidx.test:rules:$Versions.rules",
            espresso: "androidx.test.espresso:espresso-core:$Versions.espresso",
            coroutines: "org.jetbrains.kotlinx:kotlinx-coroutines-test:$Versions.coroutinesTest"
    ]

    public static final kotlinLibs = [
            stdlib: "org.jetbrains.kotlin:kotlin-stdlib:$Versions.stdlib",
    ]

    public static final camera = [
            camera2: "androidx.camera:camera-camera2:$Versions.cameraX",
            cameraLifecycle: "androidx.camera:camera-lifecycle:$Versions.cameraX",
            cameraView: "androidx.camera:camera-view:$Versions.cameraView"
    ]

    public static final machineLearningGoogleKit = [
            textRecognition: "com.google.android.gms:play-services-mlkit-text-recognition:$Versions.textRecognition",
            faceDetection: "com.google.android.gms:play-services-mlkit-face-detection:$Versions.faceDetection"
    ]

}