package com.example.network.di

import com.example.network.OffersNetworkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetroModule {

    fun interceptor(): HttpLoggingInterceptor {
        val interceptorobj = HttpLoggingInterceptor()
        interceptorobj.level = HttpLoggingInterceptor.Level.HEADERS

        return interceptorobj
    }

    fun client(): OkHttpClient {
        val clientobj = OkHttpClient().newBuilder().addInterceptor(interceptor()).build()
        return clientobj
    }

    @Provides
    fun providesBaseUrl(): String = "https://run.mocky.io/v3/"

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
//    @Singleton
    fun provideMainService(retrofit: Retrofit): OffersNetworkApi =
        retrofit.create(OffersNetworkApi::class.java)
}