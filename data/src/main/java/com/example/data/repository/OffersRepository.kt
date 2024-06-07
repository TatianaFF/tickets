package com.example.data.repository

import com.example.models.Flight
import com.example.models.Offer
import com.example.models.Ticket
import kotlinx.coroutines.flow.Flow

interface OffersRepository {

    fun getOffers(): Flow<List<Offer>>

    fun getRecommendedFlights(): Flow<List<Flight>>

    fun getTickets(): Flow<List<Ticket>>
}