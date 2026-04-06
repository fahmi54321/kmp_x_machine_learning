package com.kmpxmachinelearning.salary.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class PointEntity(
    val x: Double,
    val y: Double
)