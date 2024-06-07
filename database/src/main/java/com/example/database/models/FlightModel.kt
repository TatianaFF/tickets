package com.example.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.models.Flight
import com.example.models.Price

@Entity
data class FlightModel (
    @PrimaryKey
    val id: Int,
    val title: String,
    val timeRange: List<String>,
    val price: Long
) {
    companion object {
        fun Flight.toFlightModel(): FlightModel {
            return FlightModel(
                id = id,
                title = title,
                timeRange = timeRange,
                price = price.value
            )
        }

        fun FlightModel.toFlight(): Flight {
            return Flight(
                id = id,
                title = title,
                timeRange = timeRange,
                price = Price(price)
            )
        }
    }
}