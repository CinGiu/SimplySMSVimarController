package com.example.giuliocinelli.vimarsmscontroller.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun convertLongToTime(time: Long): String {
        val date = Date(time)

        val format = SimpleDateFormat("dd/MM/yyyy - HH:mm")
        return format.format(date)
    }

    fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }

    fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("dd/MM/yyyy - HH:mm")
        return df.parse(date).time
    }
}