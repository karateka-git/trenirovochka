package com.example.trenirovochka.domain.extensions

import com.example.trenirovochka.domain.extensions.DateConstants.DATE_FORMAT_FULL_DATE_SPACED_PATTERN
import java.text.SimpleDateFormat
import java.util.*

object DateConstants {
    const val DATE_FORMAT_FULL_DATE_SPACED_PATTERN = "d MMMM yyyy" // 31 December 2021
}

private val fullDateFormatter =
    SimpleDateFormat(DATE_FORMAT_FULL_DATE_SPACED_PATTERN, Locale.ROOT)

/**
 * @param date [Date] class instance.
 * @return formatted date time string like `31 December 2021`.
 */
fun formatAsFullDate(date: Date): String = fullDateFormatter.format(date ?: "")

/**
 * Parse date string, accepts only "d MMMM yyyy" format.
 *
 * @param input input date string.
 * @return [Date] class instance or null if parse not successful.
 */
fun parseFullDate(input: String): Date? = fullDateFormatter.parse(input)

fun Date.compareWithoutTime(anotherDate: Date): Boolean {
    val first = this.withoutTime()
    val second = anotherDate.withoutTime()

    return first <= second
}

fun Date.withoutTime(): Date =
    Calendar.getInstance().apply {
        time = this@withoutTime
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time
