package com.example.tickets.model

import com.example.models.Ticket
import com.example.tickets.adapter.DelegateAdapterItem

data class TicketItem(
    val id: Long,
    val badge: String? = null,
    val price: Long,
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
        fun Ticket.toTicketItem(): TicketItem {
            return TicketItem(
                id = id,
                badge = badge,
                price = price,
                departureDate = departureDate,
                departureAirport = departureAirport,
                arrivalDate = arrivalDate,
                arrivalAirport = arrivalAirport,
                hasTransfer = hasTransfer
            )
        }
    }
}