package com.krishworld.hiltexample.di.module

import android.content.Context
import androidx.lifecycle.ViewModel
import com.krishworld.hiltexample.data.local.AppDatabase
import com.krishworld.hiltexample.data.local.PostDao
import com.krishworld.hiltexample.data.local.repository.MainLocalRepository
import com.krishworld.hiltexample.data.local.repository.MainLocalRepositoryImpl
import com.krishworld.hiltexample.di.ViewModelKey
import com.krishworld.hiltexample.ui.main.usecase.MainUseCase
import com.krishworld.hiltexample.ui.main.usecase.MainUseCaseImpl
import com.krishworld.hiltexample.ui.main.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {

    @Binds
    abstract fun bindMainUseCase(mainUseCaseImpl: MainUseCaseImpl): MainUseCase

    @Binds
    abstract fun bindMainRemoteRepository(mainLocalRepositoryImpl: MainLocalRepositoryImpl): MainLocalRepository

    companion object {
        @Provides
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        fun provideMainViewModel(mainUseCase: MainUseCase): ViewModel {
            return MainViewModel(mainUseCase = mainUseCase)
        }

        @Provides
        fun provideUserDao(context: Context): PostDao {
            return AppDatabase.getDatabase(context).postDao()
        }
    }
}