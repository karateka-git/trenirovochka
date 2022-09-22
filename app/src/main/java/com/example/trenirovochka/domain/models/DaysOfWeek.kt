package com.example.trenirovochka.domain.models

import java.util.Calendar

enum class DaysOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    companion object {
        fun getDayOfWeek(calendar: Calendar): DaysOfWeek {
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

    fun getShortDisplayName(): String {
        return when(this) {
            MONDAY -> "ПН"
            TUESDAY -> "ВТ"
            WEDNESDAY -> "СР"
            THURSDAY -> "ЧТ"
            FRIDAY -> "ПТ"
            SATURDAY -> "СБ"
            SUNDAY -> "ВС"
        }
    }
}
