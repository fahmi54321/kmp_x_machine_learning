package com.kmpxmachinelearning.salary.presentation.viewmodel

import com.kmpxmachinelearning.salary.domain.entity.SalaryEntity

data class SalaryState(
    val salaryEntity: SalaryEntity? = null,
    val level: String = "6.5",
)