package com.example.models

import android.icu.text.CaseMap.Title
import com.google.gson.annotations.SerializedName

data class Flight(
    val id: Int,
    val title: String,
    @SerializedName("time_range")
    val timeRange: List<String>,
    val price: Price
)