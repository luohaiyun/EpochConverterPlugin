package com.github.luohaiyun.plugin.timeconverter.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object Dates {


    fun timestampToLocalDateStr(timestamp: Long): String {
        val instant = Instant.ofEpochMilli(timestamp);
        val zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone).toString();
    }

}