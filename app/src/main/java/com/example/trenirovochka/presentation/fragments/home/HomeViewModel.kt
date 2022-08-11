package com.example.trenirovochka.presentation.fragments.home

import androidx.lifecycle.*
import com.example.trenirovochka.data.local.ActionWithDate
import com.example.trenirovochka.domain.extensions.formatAsFullDate
import com.example.trenirovochka.domain.interactors.interfaces.ITrainingProgramsInteractor
import com.example.trenirovochka.domain.models.TrainingProgram
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val programsInteractor: ITrainingProgramsInteractor,
    private val calendar: Calendar
) : ViewModel() {

    private val _selectedDate: MutableLiveData<Date> by lazy {
        MutableLiveData<Date>(
            getCurrentDate()
        )
    }
    val selectedDate: LiveData<String> = _selectedDate.map { formatAsFullDate(it) }

    val trainingProgram: LiveData<TrainingProgram> = _selectedDate.switchMap {
        programsInteractor.getTrainingProgram(formatAsFullDate(it)).asLiveData()
    }

    fun updateSelectedDate(action: ActionWithDate) {
        when (action) {
            ActionWithDate.NEXT -> {
                _selectedDate.value = calendar.run {
                    add(Calendar.DAY_OF_YEAR, 1)
                    time
                }
            }
            ActionWithDate.PREV -> {
                _selectedDate.value = calendar.run {
                    add(Calendar.DAY_OF_YEAR, -1)
                    time
                }
            }
        }
    }

    private fun getCurrentDate(): Date = calendar.time
}
