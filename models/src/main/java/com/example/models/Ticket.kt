package com.example.models


data class Ticket(
    val id: Long,
    val badge: String? = null,
    val price: Long,
    val departureDate: String,
    val departureAirport: String,
    val arrivalDate: String,
    val arrivalAirport: String,
    val hasTransfer: Boolean,
)