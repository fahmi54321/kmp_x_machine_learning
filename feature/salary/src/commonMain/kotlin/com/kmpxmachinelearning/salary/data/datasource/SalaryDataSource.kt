package com.kmpxmachinelearning.salary.data.datasource

import com.kmpxmachinelearning.salary.data.model.SalaryModel

interface SalaryDataSource {
    suspend fun predictSalary(positionLevel: Double): SalaryModel?
}