package com.kmpxmachinelearning.salary.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kmpxmachinelearning.salary.domain.entity.PointEntity
import com.kmpxmachinelearning.salary.domain.entity.SalaryParamsEntity
import com.kmpxmachinelearning.salary.domain.usecase.SalaryUsecase
import com.kmpxmachinelearning.shared.base.viewmodel.BaseViewModel
import com.kmpxmachinelearning.shared.core.app.AppState
import com.kmpxmachinelearning.shared.core.service.CancelManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SalaryViewModel(
    private val salaryUsecase: SalaryUsecase,
    appState: AppState,
    cancelManager: CancelManager,
) : BaseViewModel(appState = appState, cancelManager = cancelManager) {
    private val _state = MutableStateFlow(SalaryState())
    val state: StateFlow<SalaryState> = _state

    var touchedSpot by mutableStateOf<PointEntity?>(null)
        private set

    fun updateTouchedSpot(point: PointEntity?) {
        touchedSpot = point
    }

    fun onLevelChange(value: String) {
        _state.update { it.copy(level = value) }
    }

    private fun getLevel(): Double {
        return _state.value.level.toDoubleOrNull() ?: 1.0
    }

    fun predict() {
        safeRequest(
            request = {
                salaryUsecase.predictSalary(
                    SalaryParamsEntity(positionLevel = getLevel())
                )
            },
            onSuccess = { data ->
                _state.update {
                    it.copy(salaryEntity = data)
                }
                updateTouchedSpot(data?.userPoint)
            },
            onTimeout = { message, _ ->
                appState.showTimeout(message) { predict() }
            }
        )
    }

    fun formatUSD(value: Double): String {
        return "$" + value.toLong()
            .toString()
            .reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()
    }
}