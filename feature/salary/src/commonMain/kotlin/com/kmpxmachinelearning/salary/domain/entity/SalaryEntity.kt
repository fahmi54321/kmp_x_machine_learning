package com.kmpxmachinelearning.salary.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class SalaryEntity(
    val level: String = "6.5",
    val salaryFormatted: String = "",
    val salary: Double = 0.0,
    val category: String = "",
    val recommendation: String = "",
    val curve: List<PointEntity> = emptyList(),
    val realData: List<PointEntity> = emptyList(),
    val userPoint: PointEntity? = null
)