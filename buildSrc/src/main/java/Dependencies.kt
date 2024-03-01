object Versions {
    /**
     * Core dependencies
     * */
    const val coreKtx = "1.10.1"
    const val appcompat = "1.6.1"
    const val material = "1.9.0"
    const val constraintLayout = "2.1.4"
    const val workManager = "2.8.1"

    /**
     * Core Test dependencies
     * */
    const val testImplJunit = "4.13.2"
    const val androidTestImplJunit = "1.1.5"
    const val androidTestEspresso = "3.5.1"

    /**
     * Unit Test dependencies
     * */
    const val mockk = "1.13.4"
    const val mockitoCore = "5.2.0"
    const val robolectric = "4.9.2"
    const val coroutinesTest = "1.6.4"
}

object CoreDependencies {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val workManager = "androidx.work:work-runtime-ktx:${Versions.workManager}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}

object TestImplementation {
    const val junit = "junit:junit:${Versions.testImplJunit}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCore}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
}

object AndroidTestImplementation {
    const val junit = "androidx.test.ext:junit:${Versions.androidTestImplJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.androidTestEspresso}"
}

object DaggerHilt {
    private const val hilt_dagger_version = "2.44"
    private const val hilt_compiler_version = "1.0.0"
    const val hilt = "com.google.dagger:hilt-android:$hilt_dagger_version"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$hilt_dagger_version"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:$hilt_compiler_version"
}

object Retrofit {
    private const val retrofit_version = "2.9.0"
    private const val logging_interceptor_version = "5.0.0-alpha.3"
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofit_version"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:$retrofit_version"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:$logging_interceptor_version"
}

object RoomDb {
    private const val room_version = "2.5.1"
    const val roomRuntime = "androidx.room:room-runtime:$room_version"
    const val roomRxjava = "androidx.room:room-rxjava3:$room_version"
    const val roomKtx = "androidx.room:room-ktx:$room_version"
}

object Glide {
    private const val glide_version = "4.14.2"
    const val glide = "com.github.bumptech.glide:glide:$glide_version"
}

object Navigation {
    private const val navigation_version = "2.5.3"
    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:$navigation_version"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:$navigation_version"
}

object Exoplayer {
    private const val exoplayer_version = "1.0.1"
    const val exoplayer = "androidx.media3:media3-exoplayer:$exoplayer_version"
    const val exoplayerUi = "androidx.media3:media3-ui:$exoplayer_version"
    const val exoplayerDash = "androidx.media3:media3-exoplayer-dash:$exoplayer_version"
}

object Animation {
    private const val lottie_version = "5.2.0"
    private const val dynamic_animation_version = "1.0.0"
    const val lottie = "com.airbnb.android:lottie:$lottie_version"
    const val dynamicAnimation =
        "androidx.dynamicanimation:dynamicanimation:$dynamic_animation_version"
}