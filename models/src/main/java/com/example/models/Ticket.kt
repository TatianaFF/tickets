package com.example.models

import com.google.gson.annotations.SerializedName


data class Ticket(
    val id: Long,
    val badge: String,
    val price: Price,
    @SerializedName("provider_name")
    val providerName: String,
    val company: String,
    val departure: Departure,
    val arrival: Arrival
) {
    data class Departure(
        val town: String,
        val date: String,
        val airport: String
    )

    data class Arrival(
        val departure: Departure,

    )
}