package com.example.network

import com.example.models.Flight
import com.example.models.Offer
import com.example.models.Ticket
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.Response
import javax.inject.Singleton

interface OffersNetworkApi {
    @GET("214a1713-bac0-4853-907c-a1dfc3cd05fd")
    suspend fun getOffers(): OffersResponse

    @GET("7e55bf02-89ff-4847-9eb7-7d83ef884017")
    suspend fun getRecommendedFlights(): RecommendedFlightsResponse

    @GET("670c3d56-7f03-4237-9e34-d437a9e56ebf")
    suspend fun getTickets(): TicketsResponse
}

data class OffersResponse(
    val offers: List<Offer>,
)

data class RecommendedFlightsResponse(
    @SerializedName("tickets_offers")
    val ticketsOffers: List<Flight>,
)

data class TicketsResponse(
    val tickets: List<Ticket>,
)