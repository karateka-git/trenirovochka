package com.example.trenirovochka.data.local.storage

import androidx.room.TypeConverter
import com.example.trenirovochka.domain.extensions.withoutTime
import com.example.trenirovochka.domain.models.ExecutionStatus
import java.util.*

class ExecutionStatusConverter {

    @TypeConverter
    fun fromExecutionStatus(value: ExecutionStatus) = value.name

    @TypeConverter
    fun toExecutionStatus(value: String) = enumValueOf<ExecutionStatus>(value)
}

class DateConverter {

    @TypeConverter
    fun fromDate(value: Date): Long {
        return value.withoutTime().time
    }

    @TypeConverter
    fun toDate(value: Long): Date {
        return Date(value)
    }
}
