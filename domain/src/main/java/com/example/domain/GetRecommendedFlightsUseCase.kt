package com.example.domain

import com.example.data.repository.OffersRepository
import com.example.models.Flight
import com.example.models.Offer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecommendedFlightsUseCase @Inject constructor(
    private val offersRepository: OffersRepository
) {
    fun execute(): Flow<List<Flight>> = offersRepository.getRecommendedFlights()
}