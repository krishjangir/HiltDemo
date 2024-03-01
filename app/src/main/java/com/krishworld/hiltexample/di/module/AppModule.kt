package com.krishworld.hiltexample.di.module

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.krishworld.hiltexample.base.ViewModelFactory
import com.krishworld.hiltexample.di.ViewModelKey
import com.krishworld.hiltexample.ui.audio.viewmodel.AudioRecorderViewModel
import com.krishworld.hiltexample.ui.coroutine.CoroutinesViewModel
import com.krishworld.hiltexample.ui.dialog.viewmodel.DialogTestViewModel
import com.krishworld.hiltexample.ui.localization.LocalizationViewModel
import com.krishworld.hiltexample.ui.themes.ThemeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext context: Context): Context = context

    @Provides
    fun providesViewModelFactory(viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelFactory {
        return ViewModelFactory.create(viewModels)
    }

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @IntoMap
    @ViewModelKey(ThemeViewModel::class)
    fun provideThemeViewModel(): ViewModel {
        return ThemeViewModel()
    }

    @Provides
    @IntoMap
    @ViewModelKey(LocalizationViewModel::class)
    fun provideLocalizationViewModel(): ViewModel {
        return LocalizationViewModel()
    }

    @Provides
    @IntoMap
    @ViewModelKey(DialogTestViewModel::class)
    fun provideDialogTestViewModel(): ViewModel {
        return DialogTestViewModel()
    }

    @Provides
    @IntoMap
    @ViewModelKey(AudioRecorderViewModel::class)
    fun provideAudioRecorderViewModel(): ViewModel {
        return AudioRecorderViewModel()
    }

    @Provides
    @IntoMap
    @ViewModelKey(CoroutinesViewModel::class)
    fun provideCoroutinesViewModel(): ViewModel {
        return CoroutinesViewModel()
    }
}