package com.krishworld.hiltexample.di.module

import androidx.lifecycle.ViewModel
import com.krishworld.hiltexample.di.ViewModelKey
import com.krishworld.hiltexample.ui.viewpager.usecase.ViewPagerVideosUseCase
import com.krishworld.hiltexample.ui.viewpager.usecase.ViewPagerVideosUseCaseImpl
import com.krishworld.hiltexample.ui.viewpager.viewmodel.ViewPagerViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class ViewPagerModule {

    @Binds
    abstract fun bindViewPagerVideosUseCase(iewPagerVideosUseCaseImpl: ViewPagerVideosUseCaseImpl): ViewPagerVideosUseCase

    companion object {
        @Provides
        @IntoMap
        @ViewModelKey(ViewPagerViewModel::class)
        fun provideViewPagerViewModel(viewPagerVideosUseCase: ViewPagerVideosUseCase): ViewModel {
            return ViewPagerViewModel(viewPagerVideosUseCase = viewPagerVideosUseCase)
        }
    }
}