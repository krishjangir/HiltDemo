package com.krishworld.hiltexample

import android.app.Application
import android.content.Context
import com.krishworld.hiltexample.ui.localization.LocaleUtil
import com.krishworld.hiltexample.ui.localization.Storage
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class HiltExampleApp : Application() {
    val storage: Storage by lazy {
        Storage(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(
            LocaleUtil.getLocalizedContext(
                base,
                Storage(base).getPreferredLocale()
            )
        )
    }
}