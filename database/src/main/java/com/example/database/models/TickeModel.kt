package com.example.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.models.Ticket

@Entity
data class TicketModel(
    @PrimaryKey
    val id: Long,
    val badge: String? = null,
    val price: Long,
    val departureDate: String,
    val departureAirport: String,
    val arrivalDate: String,
    val arrivalAirport: String,
    val hasTransfer: Boolean
) {
    companion object {
        fun Ticket.toTicketModel(): TicketModel {
            return TicketModel(
                id = id,
                price = price,
                badge = badge,
                departureDate = departureDate,
                departureAirport = departureAirport,
                arrivalDate = arrivalDate,
                arrivalAirport = arrivalAirport,
                hasTransfer = hasTransfer
            )
        }

        fun TicketModel.toTicket(): Ticket {
            return Ticket(
                id = id,
                price = price,
                badge = badge,
                departureDate = departureDate,
                departureAirport = departureAirport,
                arrivalDate = arrivalDate,
                arrivalAirport = arrivalAirport,
                hasTransfer = hasTransfer
            )
        }
    }
}
