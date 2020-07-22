package com.github.luohaiyun.plugin.epochconverter.util

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import java.util.concurrent.TimeUnit

object Dates {
    fun getTimeUnit(timestamp: Long): TimeUnit {

        val timestampStr = timestamp.toString()

        return if (timestampStr.length <= 10) {
            TimeUnit.SECONDS
        } else {
            TimeUnit.MILLISECONDS
        }
    }

    fun timestampToLocalDateStr(timestamp: Long): String {
        val instant = getInstant(timestamp)

        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)

        val zdt = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())

        return zdt.format(formatter)
    }

    fun timestampToGMTDateStr(timestamp: Long): String {
        val instant = getInstant(timestamp)

        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)

        val zdt = ZonedDateTime.ofInstant(instant, TimeZone.getTimeZone("GMT").toZoneId())

        return zdt.format(formatter)
    }


    fun getInstant(timestamp: Long): Instant {
        val timeUnit = getTimeUnit(timestamp)

        if (timeUnit == TimeUnit.SECONDS) {
            return Instant.ofEpochSecond(timestamp)
        } else {
            return Instant.ofEpochMilli(timestamp)
        }
    }

    fun getFromNow(timestamp: Long): String {

        val timeUnit = getTimeUnit(timestamp)

        var _timestamp = timestamp

        if(timeUnit == TimeUnit.SECONDS){
            _timestamp = timestamp * 1000
        }

        return millisToLongDHMS(System.currentTimeMillis() - _timestamp)
    }


    const val ONE_SECOND: Long = 1000
    const val SECONDS: Long = 60

    const val ONE_MINUTE = ONE_SECOND * 60
    const val MINUTES: Long = 60

    const val ONE_HOUR = ONE_MINUTE * 60
    const val HOURS: Long = 24

    const val ONE_DAY = ONE_HOUR * 24

    fun millisToLongDHMS(duration: Long): String {
        var duration = duration
        val res = StringBuffer()
        var temp: Long = 0
        return if (duration >= ONE_SECOND) {
            temp = duration / ONE_DAY
            if (temp > 0) {
                duration -= temp * ONE_DAY
                res.append(temp).append(" day").append(if (temp > 1) "s" else "")
                        .append(if (duration >= ONE_MINUTE) ", " else "")
            }
            temp = duration / ONE_HOUR
            if (temp > 0) {
                duration -= temp * ONE_HOUR
                res.append(temp).append(" hour").append(if (temp > 1) "s" else "")
                        .append(if (duration >= ONE_MINUTE) ", " else "")
            }
            temp = duration / ONE_MINUTE
            if (temp > 0) {
                duration -= temp * ONE_MINUTE
                res.append(temp).append(" minute").append(if (temp > 1) "s" else "")
            }
            if (res.toString() != "" && duration >= ONE_SECOND) {
                res.append(" and ")
            }
            temp = duration / ONE_SECOND
            if (temp > 0) {
                res.append(temp).append(" second").append(if (temp > 1) "s" else "")
            }
            res.toString()
        } else {
            "0 second"
        }
    }


}