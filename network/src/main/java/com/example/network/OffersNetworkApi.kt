package com.example.network

import com.example.models.Flight
import com.example.models.Offer
import com.example.network.models.TicketNetwork
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

interface OffersNetworkApi {
    @GET("https://drive.usercontent.google.com/u/0/uc?id=1o1nX3uFISrG1gR-jr_03Qlu4_KEZWhav&export=download")
    suspend fun getOffers(): OffersResponse

    @GET("https://drive.usercontent.google.com/u/0/uc?id=13WhZ5ahHBwMiHRXxWPq-bYlRVRwAujta&export=download")
    suspend fun getRecommendedFlights(): RecommendedFlightsResponse

    @GET("https://drive.google.com/uc?export=download&id=1HogOsz4hWkRwco4kud3isZHFQLUAwNBA")
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
    val tickets: List<TicketNetwork>,
)