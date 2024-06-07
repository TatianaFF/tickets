package com.example.tickets.model

import com.example.models.Offer
import com.example.tickets.R
import com.example.tickets.adapter.DelegateAdapterItem

data class OfferItem(
    val title: String,
    val town: String,
    val price: Long,
    val image: Int
): DelegateAdapterItem {
    override fun id(): Any {
        return title
    }

    override fun content(): Any {
        return price
    }

    companion object {
        fun offerToOfferItem(offer: Offer): OfferItem {
            return OfferItem(
                title = offer.title,
                town = offer.town,
                price = offer.price.value,
                image = when(offer.id) {
                    1 -> R.drawable.one
                    2 -> R.drawable.two
                    3 -> R.drawable.three
                    else -> 0
                }
            )
        }
    }
}