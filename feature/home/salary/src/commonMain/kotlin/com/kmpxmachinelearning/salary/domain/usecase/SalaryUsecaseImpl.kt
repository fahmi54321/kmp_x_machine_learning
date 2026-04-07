package com.kmpxmachinelearning.salary.domain.usecase

import com.kmpxmachinelearning.salary.domain.entity.SalaryEntity
import com.kmpxmachinelearning.salary.domain.entity.SalaryParamsEntity
import com.kmpxmachinelearning.salary.domain.repository.SalaryRepository
import com.kmpxmachinelearning.shared.core.network.RequestState
import kotlinx.coroutines.flow.Flow

class SalaryUsecaseImpl(
    private val salaryRepository: SalaryRepository
) : SalaryUsecase {
    override fun predictSalary(paramsSalaryEntity: SalaryParamsEntity): Flow<RequestState<SalaryEntity?>> {
        return salaryRepository.predictSalary(paramsSalaryEntity)
    }
}