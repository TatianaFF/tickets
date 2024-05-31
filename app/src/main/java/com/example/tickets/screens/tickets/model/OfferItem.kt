package com.example.tickets.screens.tickets.model

import android.net.Uri
import androidx.core.net.toUri
import com.example.models.Offer
import com.example.tickets.R
import com.example.tickets.screens.tickets.adapter.DelegateAdapterItem
import kotlinx.coroutines.selects.whileSelect

data class OfferItem(
    val title: String,
    val town: String,
    val price: String,
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
                price = offer.price.value.toString(),
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