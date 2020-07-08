package com.github.luohaiyun.plugin.timeconverter.util

import java.lang.String.format
import java.text.DateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object Dates {


    fun timestampToLocalDateStr(timestamp: Long): String {
        val instant = Instant.ofEpochMilli(timestamp);
        val zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone).toString();
    }

    fun getGMTDateStr(): String{
        return Date().toGMTString()
    }

    fun getLocalDateStr(): String{
        val format = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault())
        return format.format(Date())
    }

}