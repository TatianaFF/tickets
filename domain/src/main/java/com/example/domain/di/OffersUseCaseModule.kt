package com.example.domain.di

import com.example.data.repository.OffersRepository
import com.example.domain.GetOffersUseCase
import com.example.domain.GetRecommendedFlightsUseCase
import dagger.Module
import dagger.Provides

@Module
class OffersUseCaseModule {
    @Provides
    fun bindsGetOffersUseCase(offersRepository: OffersRepository): GetOffersUseCase {
        return GetOffersUseCase(offersRepository)
    }

    @Provides
    fun bindsGetRecommendedFlightsUseCase(offersRepository: OffersRepository): GetRecommendedFlightsUseCase {
        return GetRecommendedFlightsUseCase(offersRepository)
    }
}