package com.example.data.di

import com.example.data.repository.OffersRepository
import com.example.data.repository.OffersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsOffersRepository(
        offersRepository: OffersRepositoryImpl,
    ): OffersRepository
}