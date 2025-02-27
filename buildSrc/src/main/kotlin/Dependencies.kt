import org.gradle.api.JavaVersion

object Config{
    val compileSdkVersion = 34
    val buildToolsVersion = "34.0.2"
    val minSdkVersion = 21
    val targetSdkVersion = 34
    val sourceCompatibilityVersion = JavaVersion.VERSION_17
    val targetCompatibilityVersion = JavaVersion.VERSION_17
    val versionCode = 12
    val versionName = "1.0.12"

}

object Versions {

    const val PLUGIN_GRADLE_VERSION="8.7.2"
    const val PLUGIN_BINTRAY_VERSION="1.7.3"
    const val PLUGIN_MAVEN_VERSION="2.1"
    const val KOTLIN_VERSION = "1.8.21"

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
    const val COMMON_HELPER_VERSION="1.0.21"
    const val GLIDE_VERSION = "4.9.0"
}

object BuildDependencies {

    const val junit = "junit:junit:${Versions.JUNIT_VERSION}"
    const val gson = "com.google.code.gson:gson:${Versions.GSON_VERSION}"
    const val kotlinStdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN_VERSION}"
    const val rxjava2 = "io.reactivex.rxjava2:rxjava:${Versions.RXJAVA2_VERSION}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.RXANDROID_VERSION}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}"
    const val retrofitConverterScalars= "com.squareup.retrofit2:converter-scalars:${Versions.RETROFIT_VERSION}"
    const val retrofitAdapterRxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.RETROFIT_VERSION}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT_VERSION}"
    const val autodispose= "com.uber.autodispose:autodispose-android-archcomponents:${Versions.AUTODISPOSE_VERSION}"
    const val okhttp= "com.squareup.okhttp3:okhttp:${Versions.OKHTTP_VERSION}"
    const val commonhelper= "io.github.kongpf8848:common-helper:${Versions.COMMON_HELPER_VERSION}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.GLIDE_VERSION}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.GLIDE_VERSION}"
    const val glideIntegration="com.github.bumptech.glide:okhttp3-integration:${Versions.GLIDE_VERSION}"
}

object AndroidX {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.ANDROIDX_APP_COMPAT_VERSION}"
    const val legacyV4 = "androidx.legacy:legacy-support-v4:${Versions.ANDROIDX_LEGACY_V4_VERSION}"
    const val legacyV13 = "androidx.legacy:legacy-support-v13:${Versions.ANDROIDX_LEGACY_V13_VERSION}"
    const val multidex = "androidx.multidex:multidex:${Versions.ANDROIDX_MULTIDEX_VERSION}"
    const val material = "com.google.android.material:material:${Versions.ANDROIDX_MATERIAL_VERSION}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.ANDROIDX_RECYCLERVIEW_VERSION}"
    const val preference = "androidx.preference:preference:${Versions.ANDROIDX_PREFERENCE_VERSION}"
    const val cardview = "androidx.cardview:cardview:${Versions.ANDROIDX_CARDVIEW_VERSION}"
    const val gridlayout = "androidx.gridlayout:gridlayout:${Versions.ANDROIDX_GRIDLAYOUT_VERSION}"
    const val annotationX = "androidx.annotation:annotation:${Versions.ANDROIDX_ANNOTATION_VERSION}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.ANDROIDX_CONSTRAINTLAYOUT_VERSION}"
    const val paging = "androidx.paging:paging-runtime:${Versions.ANDROIDX_PAGING_VERSION}"
    const val work = "androidx.work:work-runtime:${Versions.ANDROIDX_WORK_RUNTIME_VERSION}"
    const val coreKtx="androidx.core:core-ktx:${Versions.ANDROIDX_CORE_KTX_VERSION}"
    const val room="androidx.room:room-runtime:${Versions.ANDROIDX_ROOM_VERSION}"
    const val roomKtx="androidx.room:room-ktx:${Versions.ANDROIDX_ROOM_VERSION}"
    const val roomCompiler="androidx.room:room-compiler:${Versions.ANDROIDX_ROOM_VERSION}"
    const val roomRxJava2= "androidx.room:room-rxjava2:${Versions.ANDROIDX_ROOM_VERSION}"
    const val coordinatorlayout="androidx.coordinatorlayout:coordinatorlayout:${Versions.ANDROIDX_COORDINATORLAYOUT_VERSION}"
    const val databindingCommon="androidx.databinding:databinding-common:${Versions.ANDROIDX_DATABINDING_VERSION}"
    const val databindingAdapters="androidx.databinding:databinding-adapters:${Versions.ANDROIDX_DATABINDING_VERSION}"
    const val databindingRuntime="androidx.databinding:databinding-runtime:${Versions.ANDROIDX_DATABINDING_VERSION}"
    const val activity="androidx.activity:activity:${Versions.ANDROIDX_ACTIVITY_VERSION}"
    const val activityKtx="androidx.activity:activity-ktx:${Versions.ANDROIDX_ACTIVITY_VERSION}"
    const val fragment="androidx.fragment:fragment:${Versions.ANDROIDX_FRAGMENT_VERSION}"
    const val fragmentKtx="androidx.fragment:fragment-ktx:${Versions.ANDROIDX_FRAGMENT_VERSION}"

}



