package com.example.domain

import com.example.data.repository.OffersRepository
import com.example.models.Offer
import com.example.models.Ticket
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(
    private val offersRepository: OffersRepository
) {
    fun execute(): Flow<List<Ticket>> = offersRepository.getTickets()
}