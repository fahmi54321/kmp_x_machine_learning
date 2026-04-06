package com.kmpxmachinelearning.salary.domain.repository

import com.kmpxmachinelearning.salary.domain.entity.SalaryEntity
import com.kmpxmachinelearning.salary.domain.entity.SalaryParamsEntity
import com.kmpxmachinelearning.shared.core.network.RequestState
import kotlinx.coroutines.flow.Flow

interface SalaryRepository {
    fun predictSalary(paramsSalaryEntity: SalaryParamsEntity): Flow<RequestState<SalaryEntity?>>
}