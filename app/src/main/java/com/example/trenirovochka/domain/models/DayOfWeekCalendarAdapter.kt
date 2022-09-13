package com.example.trenirovochka.domain.models

import java.util.*

enum class DayOfWeekCalendarAdapter(val calendarDayOfWeekValue: Int) {
    MONDAY(Calendar.MONDAY),
    TUESDAY(Calendar.TUESDAY),
    WEDNESDAY(Calendar.WEDNESDAY),
    THURSDAY(Calendar.THURSDAY),
    FRIDAY(Calendar.FRIDAY),
    SATURDAY(Calendar.SATURDAY),
    SUNDAY(Calendar.SUNDAY);

    companion object {
        fun getDayOfWeek(calendar: Calendar): DayOfWeekCalendarAdapter {
            return values().first {
                it.calendarDayOfWeekValue == calendar.get(Calendar.DAY_OF_WEEK)
            }
        }
    }
}
