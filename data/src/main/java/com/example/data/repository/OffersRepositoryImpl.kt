package com.example.data.repository

import com.example.database.MyDao
import com.example.database.models.FlightModel.Companion.toFlight
import com.example.database.models.FlightModel.Companion.toFlightModel
import com.example.database.models.OfferModel.Companion.toOffer
import com.example.database.models.OfferModel.Companion.toOfferModel
import com.example.database.models.TicketModel.Companion.toTicket
import com.example.database.models.TicketModel.Companion.toTicketModel
import com.example.models.Flight
import com.example.models.Offer
import com.example.models.Ticket
import com.example.network.OffersNetworkApi
import com.example.network.models.TicketNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class OffersRepositoryImpl @Inject constructor(
    private val network: OffersNetworkApi,
    private val dao: MyDao
) : OffersRepository {

    override fun getOffers(): Flow<List<Offer>> = channelFlow {
        val offersNetwork = network.getOffers().offers

        offersNetwork.map {
            dao.insertOffer(it.toOfferModel())
        }

        dao.getOffers().collectLatest { listDB ->
            send(listDB.map { it.toOffer() })
        }
    }

    override fun getRecommendedFlights(): Flow<List<Flight>> = channelFlow {
        val flightsNetwork = network.getRecommendedFlights().ticketsOffers

        flightsNetwork.map {
            dao.insertFlight(it.toFlightModel())
        }

        dao.getFlights().collectLatest { listDB ->
            send(listDB.map { it.toFlight() })
        }
    }

    override fun getTickets(): Flow<List<Ticket>> = channelFlow {
        val ticketsNetwork = network.getTickets().tickets.map { ticketNetworkToTicket(it) }

        ticketsNetwork.map {
            dao.insertTicket(it.toTicketModel())
        }

        dao.getTickets().collectLatest { listDB ->
            send(listDB.map { it.toTicket() })
        }
    }

    private fun ticketNetworkToTicket(ticketNetwork: TicketNetwork): Ticket {
        return with(ticketNetwork) {
            Ticket(
                id = id,
                badge = badge,
                price = price.value,
                departureDate = departure.date,
                departureAirport = departure.airport,
                arrivalDate = arrival.date,
                arrivalAirport = arrival.airport,
                hasTransfer = hasTransfer
            )
        }
    }
}