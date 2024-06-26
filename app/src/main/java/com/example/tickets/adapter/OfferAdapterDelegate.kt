package com.example.tickets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tickets.databinding.ItemOfferBinding
import com.example.tickets.model.OfferItem
import java.text.NumberFormat
import java.util.Locale

class OfferAdapterDelegate : DelegateAdapter<OfferItem, OfferAdapterDelegate.OfferViewHolder>(
    OfferItem::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferViewHolder(binding)
    }

    override fun bindViewHolder(
        model: OfferItem,
        viewHolder: OfferViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(model)
    }

    inner class OfferViewHolder(private val binding: ItemOfferBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OfferItem) {
            with(binding) {
                imgOffer.setImageResource(item.image)
                tvTitleOffer.text = item.title
                tvTown.text = item.town
                tvPrice.text = NumberFormat.getInstance(Locale("ru")).format(item.price)
            }
        }
    }
}