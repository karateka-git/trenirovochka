package com.example.trenirovochka.domain.models

import java.util.*

enum class DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    companion object {
        fun getDayOfWeek(calendar: Calendar): DayOfWeek {
            return when (calendar.get(Calendar.DAY_OF_WEEK)) {
                Calendar.MONDAY -> MONDAY
                Calendar.TUESDAY -> TUESDAY
                Calendar.WEDNESDAY -> WEDNESDAY
                Calendar.THURSDAY -> THURSDAY
                Calendar.FRIDAY -> FRIDAY
                Calendar.SATURDAY -> SATURDAY
                else -> SUNDAY
            }
        }
    }
}
