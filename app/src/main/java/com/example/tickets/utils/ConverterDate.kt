package com.example.tickets.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object ConverterDate {
    @RequiresApi(Build.VERSION_CODES.O)
    fun stringToLocalDateTime(str: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        val dt = LocalDateTime.parse(str, formatter)
        return dt
    }
}