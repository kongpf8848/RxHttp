import org.gradle.api.JavaVersion

object Config{
    val compileSdkVersion = 30
    val buildToolsVersion = "30.0.2"
    val minSdkVersion = 21
    val targetSdkVersion = 30
    val sourceCompatibilityVersion = JavaVersion.VERSION_1_8
    val targetCompatibilityVersion = JavaVersion.VERSION_1_8
    val versionCode = 1
    val versionName = "1.0.0"

}

object Versions {

    const val PLUGIN_GRADLE_VERSION="3.6.3"
    const val KOTLIN_VERSION = "1.3.60"

    const val ANDROIDX_APP_COMPAT_VERSION = "1.2.0"
    const val ANDROIDX_LEGACY_V4_VERSION = "1.0.0"
    const val ANDROIDX_LEGACY_V13_VERSION = "1.0.0"
    const val ANDROIDX_MULTIDEX_VERSION = "2.0.0"
    const val ANDROIDX_MATERIAL_VERSION = "1.0.0"
    const val ANDROIDX_RECYCLERVIEW_VERSION = "1.0.0"
    const val ANDROIDX_PREFERENCE_VERSION = "1.0.0"
    const val ANDROIDX_CARDVIEW_VERSION = "1.0.0"
    const val ANDROIDX_GRIDLAYOUT_VERSION = "1.0.0"
    const val ANDROIDX_ANNOTATION_VERSION = "1.1.0"
    const val ANDROIDX_CONSTRAINTLAYOUT_VERSION = "1.1.3"
    const val ANDROIDX_PAGING_VERSION = "2.0.0"
    const val ANDROIDX_WORK_RUNTIME_VERSION = "2.0.1"
    const val ANDROIDX_CORE_KTX_VERSION="1.3.1"
    const val ANDROIDX_ROOM_VERSION="2.2.5"
    const val ANDROIDX_COORDINATORLAYOUT_VERSION="1.1.0"
    const val ANDROIDX_DATABINDING_VERSION="4.1.0"
    const val ANDROIDX_ACTIVITY_VERSION="1.2.0-alpha06"
    const val ANDROIDX_FRAGMENT_VERSION="1.3.0-alpha06"

    const val GSON_VERSION = "2.8.2"
    const val JUNIT_VERSION = "4.12"
    const val RXJAVA2_VERSION = "2.1.9"
    const val RXANDROID_VERSION = "2.0.2"
    const val RETROFIT_VERSION = "2.9.0"
    const val OKHTTP_VERSION = "4.9.0"
    const val AUTODISPOSE_VERSION="1.4.0"
    const val COMMON_HELPER_VERSION="1.0.20"

}

object BuildDependencies {

    val junit = "junit:junit:${Versions.JUNIT_VERSION}"
    val gson = "com.google.code.gson:gson:${Versions.GSON_VERSION}"
    val kotlinStdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN_VERSION}"
    val rxjava2 = "io.reactivex.rxjava2:rxjava:${Versions.RXJAVA2_VERSION}"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.RXANDROID_VERSION}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}"
    val retrofitConverterScalars= "com.squareup.retrofit2:converter-scalars:${Versions.RETROFIT_VERSION}"
    val retrofitAdapterRxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.RETROFIT_VERSION}"
    val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT_VERSION}"
    val autodispose= "com.uber.autodispose:autodispose-android-archcomponents:${Versions.AUTODISPOSE_VERSION}"
    val okhttp= "com.squareup.okhttp3:okhttp:${Versions.OKHTTP_VERSION}"
    val commonhelper= "com.kongpf.commonhelper:common-helper:${Versions.COMMON_HELPER_VERSION}"
}

object AndroidX {
    val appCompat = "androidx.appcompat:appcompat:${Versions.ANDROIDX_APP_COMPAT_VERSION}"
    val legacyV4 = "androidx.legacy:legacy-support-v4:${Versions.ANDROIDX_LEGACY_V4_VERSION}"
    val legacyV13 = "androidx.legacy:legacy-support-v13:${Versions.ANDROIDX_LEGACY_V13_VERSION}"
    val multidex = "androidx.multidex:multidex:${Versions.ANDROIDX_MULTIDEX_VERSION}"
    val material = "com.google.android.material:material:${Versions.ANDROIDX_MATERIAL_VERSION}"
    val recyclerview = "androidx.recyclerview:recyclerview:${Versions.ANDROIDX_RECYCLERVIEW_VERSION}"
    val preference = "androidx.preference:preference:${Versions.ANDROIDX_PREFERENCE_VERSION}"
    val cardview = "androidx.cardview:cardview:${Versions.ANDROIDX_CARDVIEW_VERSION}"
    val gridlayout = "androidx.gridlayout:gridlayout:${Versions.ANDROIDX_GRIDLAYOUT_VERSION}"
    val annotationX = "androidx.annotation:annotation:${Versions.ANDROIDX_ANNOTATION_VERSION}"
    val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.ANDROIDX_CONSTRAINTLAYOUT_VERSION}"
    val paging = "androidx.paging:paging-runtime:${Versions.ANDROIDX_PAGING_VERSION}"
    val work = "androidx.work:work-runtime:${Versions.ANDROIDX_WORK_RUNTIME_VERSION}"
    val coreKtx="androidx.core:core-ktx:${Versions.ANDROIDX_CORE_KTX_VERSION}"
    val room="androidx.room:room-runtime:${Versions.ANDROIDX_ROOM_VERSION}"
    val roomKtx="androidx.room:room-ktx:${Versions.ANDROIDX_ROOM_VERSION}"
    val roomCompiler="androidx.room:room-compiler:${Versions.ANDROIDX_ROOM_VERSION}"
    val roomRxJava2= "androidx.room:room-rxjava2:${Versions.ANDROIDX_ROOM_VERSION}"
    val coordinatorlayout="androidx.coordinatorlayout:coordinatorlayout:${Versions.ANDROIDX_COORDINATORLAYOUT_VERSION}"
    val databindingCommon="androidx.databinding:databinding-common:${Versions.ANDROIDX_DATABINDING_VERSION}"
    val databindingAdapters="androidx.databinding:databinding-adapters:${Versions.ANDROIDX_DATABINDING_VERSION}"
    val databindingRuntime="androidx.databinding:databinding-runtime:${Versions.ANDROIDX_DATABINDING_VERSION}"
    val activity="androidx.activity:activity:${Versions.ANDROIDX_ACTIVITY_VERSION}"
    val activityKtx="androidx.activity:activity-ktx:${Versions.ANDROIDX_ACTIVITY_VERSION}"
    val fragment="androidx.fragment:fragment:${Versions.ANDROIDX_FRAGMENT_VERSION}"
    val fragmentKtx="androidx.fragment:fragment-ktx:${Versions.ANDROIDX_FRAGMENT_VERSION}"

}



