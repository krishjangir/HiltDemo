package com.krishworld.hiltexample.di.module

import androidx.lifecycle.ViewModel
import com.krishworld.hiltexample.di.ViewModelKey
import com.krishworld.hiltexample.ui.video.usecase.VideosUseCase
import com.krishworld.hiltexample.ui.video.usecase.VideosUseCaseImpl
import com.krishworld.hiltexample.ui.video.viewmodel.VideoViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class VideoModule {

    @Binds
    abstract fun bindVideosUseCase(videosUseCaseImpl: VideosUseCaseImpl): VideosUseCase

    companion object {
        @Provides
        @IntoMap
        @ViewModelKey(VideoViewModel::class)
        fun provideVideoViewModel(videosUseCase: VideosUseCase): ViewModel {
            return VideoViewModel(videosUseCase = videosUseCase)
        }
    }
}