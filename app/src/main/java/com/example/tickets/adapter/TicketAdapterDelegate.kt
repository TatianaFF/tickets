package com.example.tickets.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.runtime.isTraceInProgress
import androidx.datastore.preferences.protobuf.Timestamp
import androidx.recyclerview.widget.RecyclerView
import com.example.tickets.databinding.ItemTicketBinding
import com.example.tickets.model.TicketItem
import com.example.tickets.utils.ConverterDate.stringToLocalDateTime
import java.text.NumberFormat
import java.time.LocalDateTime
import java.util.Locale
import java.time.temporal.ChronoUnit
import kotlin.math.abs

class TicketAdapterDelegate : DelegateAdapter<TicketItem, TicketAdapterDelegate.TicketViewHolder>(
    TicketItem::class.java) {

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

    inner class TicketViewHolder(private val binding: ItemTicketBinding): RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: TicketItem) {
            val ldtDeparture = stringToLocalDateTime(item.departureDate)
            val ldtArrival = stringToLocalDateTime(item.arrivalDate)

            with(binding) {
                tvPrice.text = NumberFormat.getInstance(Locale("ru")).format(item.price.value) + " ₽"
                tvDepartureDate.text = ldtDeparture.hour.toString() + ":" + ldtDeparture.minute.toString()
                tvArrivalDate.text = ldtArrival.hour.toString() + ":" + ldtArrival.minute.toString()
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

        return "${diffMin/60}.${diffMin%60}ч в пути"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun lgtToMinutes(ldt: LocalDateTime): Int {
        return ldt.dayOfMonth * 24 * 60 + ldt.hour * 60 + ldt.minute
    }
}