package com.example.data.repository

import com.example.models.Flight
import com.example.models.Offer
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call

interface OffersRepository {
    fun getOffers(): Flow<List<Offer>>

    fun getRecommendedFlights(): Flow<List<Flight>>
}