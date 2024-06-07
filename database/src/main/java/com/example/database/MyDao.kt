package com.example.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.models.FlightModel
import com.example.database.models.OfferModel
import com.example.database.models.TicketModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {
    @Query("SELECT * FROM offermodel")
    fun getOffers(): Flow<List<OfferModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOffer(offer: OfferModel)


    @Query("SELECT * FROM ticketmodel")
    fun getTickets(): Flow<List<TicketModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: TicketModel)


    @Query("SELECT * FROM flightmodel")
    fun getFlights(): Flow<List<FlightModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlight(flight: FlightModel)
}