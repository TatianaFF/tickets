package com.example.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.models.Offer
import com.example.models.Price

@Entity
data class OfferModel (
    @PrimaryKey
    val id: Int,
    val title: String,
    val town: String,
    val price: Long
) {
    companion object {
        fun Offer.toOfferModel(): OfferModel {
            return OfferModel(
                id = id,
                title = title,
                town = town,
                price = price.value
            )
        }

        fun OfferModel.toOffer(): Offer {
            return Offer(
                id = id,
                title = title,
                town = town,
                price = Price(price)
            )
        }
    }
}
