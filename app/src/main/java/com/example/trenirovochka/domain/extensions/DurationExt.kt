package com.example.trenirovochka.domain.extensions

import com.example.trenirovochka.domain.extensions.TimeConstants.TIME_DATE_FORMAT_SHORT_PATTERN
import com.example.trenirovochka.domain.extensions.TimeConstants.TIME_DURATION_FORMAT_SHORT_PATTERN
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object TimeConstants {
    const val TIME_DURATION_FORMAT_SHORT_PATTERN = "%02d:%02d" // 00:00
    const val TIME_DATE_FORMAT_SHORT_PATTERN = "mm:ss" // 00:00
}

private val timeDateShortFormatter =
    SimpleDateFormat(TIME_DATE_FORMAT_SHORT_PATTERN, Locale.ROOT)

/**
 * @param duration [Duration] class instance.
 * @return formatted date time string like `00:00`.
 */
fun formatAsTime(duration: Duration): String =
    String.format(
        TIME_DURATION_FORMAT_SHORT_PATTERN,
        duration.inWholeSeconds / 60,
        duration.inWholeSeconds % 60
    )

/**
 * Parse date string, accepts only "00:00" format.
 *
 * @param input input date string.
 * @return [Duration] class instance or null if parse not successful.
 */
fun parseTime(input: String): Duration? {
    val date = timeDateShortFormatter.parse(input) ?: return null
    val calendar = Calendar.getInstance().apply {
        time = date
    }
    return calendar.get(Calendar.MINUTE).toDuration(DurationUnit.MINUTES) +
        calendar.get(Calendar.SECOND).toDuration(DurationUnit.SECONDS)
}
