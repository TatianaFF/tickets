package com.example.tickets.model

import androidx.compose.foundation.isSystemInDarkTheme
import com.example.models.Offer
import com.example.models.Price
import com.example.models.Ticket
import com.example.tickets.R
import com.example.tickets.adapter.DelegateAdapterItem

data class TicketItem(
    val id: Long,
    val badge: String? = null,
    val price: Price,
    val departureDate: String,
    val departureAirport: String,
    val arrivalDate: String,
    val arrivalAirport: String,
    val hasTransfer: Boolean,
): DelegateAdapterItem {

    override fun id(): Any {
        return id
    }

    override fun content(): Any {
        return price
    }

    companion object {
        fun ticketToTicketItem(ticket: Ticket): TicketItem {
            return TicketItem(
                id = ticket.id,
                badge = ticket.badge,
                price = ticket.price,
                departureDate = ticket.departure.date,
                departureAirport = ticket.departure.airport,
                arrivalDate = ticket.arrival.date,
                arrivalAirport = ticket.arrival.airport,
                hasTransfer = ticket.hasTransfer
            )
        }
    }
}