package com.example.tickets.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.tickets.databinding.ItemTicketBinding
import com.example.tickets.model.TicketItem
import com.example.tickets.utils.ConverterDate.stringToLocalDateTime
import java.text.NumberFormat
import java.time.LocalDateTime
import java.util.Locale
import kotlin.math.abs

class TicketAdapterDelegate : DelegateAdapter<TicketItem, TicketAdapterDelegate.TicketViewHolder>(
    TicketItem::class.java
) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun bindViewHolder(
        model: TicketItem,
        viewHolder: TicketViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(model)
    }

    inner class TicketViewHolder(private val binding: ItemTicketBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: TicketItem) {
            val ldtDeparture = stringToLocalDateTime(item.departureDate)
            val ldtArrival = stringToLocalDateTime(item.arrivalDate)

            with(binding) {
                tvPrice.text = NumberFormat.getInstance(Locale("ru")).format(item.price)
                tvDepartureDate.text = addZero(ldtDeparture.hour) + ":" + addZero(ldtDeparture.minute)
                tvArrivalDate.text = addZero(ldtArrival.hour) + ":" + addZero(ldtArrival.minute)
                tvDepartureAirport.text = item.departureAirport
                tvArrivalAirport.text = item.arrivalAirport
                if (item.badge != null) {
                    badge.visibility = View.VISIBLE
                    tvBadge.text = item.badge
                }
                if (!item.hasTransfer) {
                    transfer.visibility = View.VISIBLE
                }

                tvFlightTime.text = calcTravelTime(ldtDeparture, ldtArrival)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calcTravelTime(dtDeparture: LocalDateTime, dtArrival: LocalDateTime): String {
        val minDep = lgtToMinutes(dtDeparture)
        val minArr = lgtToMinutes(dtArrival)
        val diffMin = abs(minDep - minArr)

        return "${diffMin / 60}.${diffMin % 60}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun lgtToMinutes(ldt: LocalDateTime): Int {
        return ldt.dayOfMonth * 24 * 60 + ldt.hour * 60 + ldt.minute
    }

    private fun addZero(numb: Int): String {
        return if (numb in 0..9) "0$numb" else numb.toString()
    }
}