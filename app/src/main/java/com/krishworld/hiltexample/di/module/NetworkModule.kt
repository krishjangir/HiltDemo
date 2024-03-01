package com.krishworld.hiltexample.di.module

import androidx.lifecycle.ViewModel
import com.krishworld.hiltexample.data.remote.ApiService
import com.krishworld.hiltexample.data.remote.CustomHeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.krishworld.hiltexample.BuildConfig
import com.krishworld.hiltexample.di.ViewModelKey
import com.krishworld.hiltexample.ui.networapikcall.repository.NetworkApiRepository
import com.krishworld.hiltexample.ui.networapikcall.repository.NetworkApiRepositoryImpl
import com.krishworld.hiltexample.ui.networapikcall.usecase.NetworkApiUseCase
import com.krishworld.hiltexample.ui.networapikcall.usecase.NetworkApiUseCaseImpl
import com.krishworld.hiltexample.ui.networapikcall.viewmodel.NetworkApiViewModel
import dagger.Binds
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineDispatcher


@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    companion object {
        private const val TIMEOUT_READ = 30   //In seconds
        private const val TIMEOUT_CONNECT = 30 //In seconds

        @Provides
        @Singleton
        fun provideCustomHeaderInterceptor(): CustomHeaderInterceptor {
            return CustomHeaderInterceptor()
        }

        @Provides
        @Singleton
        fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            }
            return loggingInterceptor
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(
            interceptor: CustomHeaderInterceptor,
            loggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_CONNECT.toLong(), TimeUnit.SECONDS)
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }


        @Provides
        @IntoMap
        @ViewModelKey(NetworkApiViewModel::class)
        fun provideNetworkApiViewModel(
            networkApiUseCase: NetworkApiUseCase,
            coroutineDispatcher: CoroutineDispatcher
        ): ViewModel {
            return NetworkApiViewModel(
                networkApiUseCase = networkApiUseCase,
                coroutineDispatcher = coroutineDispatcher
            )
        }
    }

    @Binds
    abstract fun bindNetworkApiRepository(networkApiRepositoryImpl: NetworkApiRepositoryImpl): NetworkApiRepository

    @Binds
    abstract fun bindNetworkApiUseCase(networkApiUseCaseImpl: NetworkApiUseCaseImpl): NetworkApiUseCase

}