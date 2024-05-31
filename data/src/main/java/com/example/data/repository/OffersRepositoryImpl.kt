package com.example.data.repository

import android.util.Log
import com.example.models.Flight
import com.example.models.Offer
import com.example.models.Ticket
import com.example.network.OffersNetworkApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import javax.inject.Inject

class OffersRepositoryImpl @Inject constructor(
    private val network: OffersNetworkApi
): OffersRepository {

    override fun getOffers(): Flow<List<Offer>> = flow {
        emit(network.getOffers().offers)
    }

    override fun getRecommendedFlights(): Flow<List<Flight>> = flow {
        emit(network.getRecommendedFlights().ticketsOffers)
    }

    override fun getTickets(): Flow<List<Ticket>> = flow {
        emit(network.getTickets().tickets)
    }
}