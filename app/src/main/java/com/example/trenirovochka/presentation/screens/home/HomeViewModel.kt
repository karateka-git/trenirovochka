package com.example.trenirovochka.presentation.screens.home

import androidx.lifecycle.*
import com.example.trenirovochka.data.local.models.ActionWithDate
import com.example.trenirovochka.domain.extensions.formatAsFullDate
import com.example.trenirovochka.domain.interactors.interfaces.ICompletedTrainingProgramsInteractor
import com.example.trenirovochka.domain.interactors.interfaces.ITrainingProgramsInteractor
import com.example.trenirovochka.domain.models.Program
import com.example.trenirovochka.domain.models.TrainingPlan
import com.example.trenirovochka.domain.models.TrainingProgram
import com.example.trenirovochka.presentation.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val completedProgramsInteractor: ICompletedTrainingProgramsInteractor,
    private val programsInteractor: ITrainingProgramsInteractor,
    private val calendar: Calendar
) : BaseViewModel() {

    private val _selectedDate: MutableLiveData<Date> by lazy {
        MutableLiveData<Date>(
            getCurrentDate()
        )
    }
    val selectedDate: LiveData<String> = _selectedDate.map { formatAsFullDate(it) }

    val trainingPlan: LiveData<TrainingPlan> = programsInteractor.getTrainingPlan().asLiveData()

    private val completedTrainingProgram: LiveData<Program?> = _selectedDate.switchMap {
        completedProgramsInteractor.get(it).asLiveData()
    }

    private val trainingProgramFromPlan: LiveData<Program> = _selectedDate.switchMap {
        programsInteractor.getTrainingProgram(it).asLiveData()
    }

    val trainingProgram: MediatorLiveData<Program> = MediatorLiveData<Program>().apply {
        addSource(completedTrainingProgram) {
            it?.let {
                value = it
            }
        }
        addSource(trainingProgramFromPlan) {
            if (completedTrainingProgram.value == null) {
                value = it
            }
        }
    }
    val isCancelActiveTrainingProgramDialogShow: MutableLiveData<Boolean> = MutableLiveData()

    fun onEditTrainingPlanClick() {
        trainingPlan.value?.let {
            navigateTo(
                HomeFragmentDirections.actionHomeFragmentToEditTrainingPlanFragment(it.id)
            )
        }
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

    fun onStartTrainingButtonClick() {
        navigateTo(
            HomeFragmentDirections.actionHomeFragmentToPerformTraining()
        )
    }

    fun onCancelTrainingButtonClick() {
        isCancelActiveTrainingProgramDialogShow.value = true
    }

    fun onContinueTrainingButtonClick(activeTrainingProgram: TrainingProgram?) {
        activeTrainingProgram?.let {
            navigateTo(
                HomeFragmentDirections.actionHomeFragmentToPerformTraining()
            )
        }
    }

    private fun getCurrentDate(): Date = calendar.time
}
