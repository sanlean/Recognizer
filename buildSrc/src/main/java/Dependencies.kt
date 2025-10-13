object Support{
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val wiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefresh}"
    const val cardview = "androidx.cardview:cardview:${Versions.cardview}"
    const val corektx = "androidx.core:core-ktx:${Versions.corektx}"
    const val collectionsKtx = "androidx.collection:collection-ktx:${Versions.collectionsktx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleKtx}"
}

object Lifecycle{
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel:${Versions.lifecycle}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata:${Versions.lifecycle}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
}

object Design{
    const val material = "com.google.android.material:material:${Versions.material}"
    const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
    const val pagingRxjava = "androidx.paging:paging-rxjava2:${Versions.paging}"
}

object Tests{
    const val junit = "junit:junit:${Versions.junit}"
    const val androidxJUnit = "androidx.test.ext:junit:${Versions.androidxJunit}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    const val truth = "com.google.truth:truth:${Versions.truth}"
    const val androidCore = "androidx.arch.core:core-testing:${Versions.coreTesting}"
    const val coreKtx = "androidx.test:core-ktx:${Versions.coreKtxTest}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val rules = "androidx.test:rules:${Versions.rules}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
}

object KotlinLibs{
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.stdlib}"
}

object Camera{
    const val camera2 = "androidx.camera:camera-camera2:${Versions.cameraX}"
    const val cameraLifecycle = "androidx.camera:camera-lifecycle:${Versions.cameraX}"
    const val cameraView = "androidx.camera:camera-view:${Versions.cameraView}"
}

object MachineLearningGoogleKit{
    const val textRecognition = "com.google.android.gms:play-services-mlkit-text-recognition:${Versions.textRecognition}"
    const val faceDetection = "com.google.android.gms:play-services-mlkit-face-detection:${Versions.faceDetection}"
}

object Plugins {
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "org.jetbrains.kotlin.android"
    const val kotlinParcelize = "kotlin-parcelize"
    const val kotlinKsp = "com.google.devtools.ksp"
    const val composeCompiler = "org.jetbrains.kotlin.plugin.compose"
    const val detekt = "io.gitlab.arturbosch.detekt"
}