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

    public static final database = [
            room: "androidx.room:room-runtime:$Versions.room",
            roomKotlin: "androidx.room:room-ktx:$Versions.room",
            roomRxJava: "androidx.room:room-rxjava2:$Versions.room"
    ]

    public static final unitTests = [
            junit: "junit:junit:$Versions.junit",
            roomTests: "androidx.room:room-testing:$Versions.room",
            robolectric: "org.robolectric:robolectric:$Versions.robolectric",
            truth: "com.google.truth:truth:$Versions.truth",
            androidCore: "android.arch.core:core-testing:$Versions.coreTesting",
            mockk: "io.mockk:mockk:$Versions.mockk"
    ]

    public static final instrumentedTests = [
            junit: "androidx.test.ext:junit:$Versions.jUnit",
            espresso: "androidx.test.espresso:espresso-core:$Versions.espresso"
    ]

    public static final kotlinLibs = [
            stdlib: "org.jetbrains.kotlin:kotlin-stdlib:$Versions.stdlib",
    ]

    public static final networking = [
            retorfit: "com.squareup.retrofit2:retrofit:$Versions.retrofit",
            gsonConverter: "com.squareup.retrofit2:converter-gson:$Versions.retrofit",
            okhttp: "com.squareup.okhttp3:okhttp:$Versions.okhttp",
            okhttpLoggingInterceptor: "com.squareup.okhttp3:logging-interceptor:$Versions.okhttp"
    ]

    public static final compilers = [
            roomCompiler: "android.arch.persistence.room:compiler:$Versions.room",
            daggerCompiler: "com.google.dagger:hilt-android-compiler:$Versions.hilt",
            hiltCompiler: "androidx.hilt:hilt-compiler:$Versions.hiltLifecycle",
            lifecycleCompiler: "androidx.lifecycle:lifecycle-compiler:$Versions.lifecycleCompiler"
    ]

    public static final dependencyInjection = [
            hilt: "com.google.dagger:hilt-android:$Versions.hilt",
            hiltLifecycle: "androidx.hilt:hilt-lifecycle-viewmodel:$Versions.hiltLifecycle"
    ]

    public static final rxjava = [
            rxjava: "io.reactivex.rxjava2:rxandroid:$Versions.rxjava",
            rxjavaRetrofitAdapter: "com.squareup.retrofit2:adapter-rxjava2:$Versions.retrofit"
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

    public static final picasso = "com.squareup.picasso:picasso:$Versions.picasso"
    public static final gson = "com.google.code.gson:gson:$Versions.gson"
}