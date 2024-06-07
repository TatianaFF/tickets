package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.models.FlightModel
import com.example.database.models.OfferModel
import com.example.database.models.TicketModel

@Database(entities = [OfferModel::class, TicketModel::class, FlightModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun myDao(): MyDao
}