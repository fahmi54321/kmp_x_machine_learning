package com.kmpxmachinelearning.salary.data.mapper

import com.kmpxmachinelearning.salary.data.model.SalaryModel
import com.kmpxmachinelearning.salary.domain.entity.PointEntity
import com.kmpxmachinelearning.salary.domain.entity.SalaryEntity

fun SalaryModel.toEntity(): SalaryEntity {
    return SalaryEntity(
        level = input.positionLevel.toString(),
        salaryFormatted = prediction.formatted,
        salary = prediction.salary,
        category = insight.category,
        recommendation = insight.recommendation,
        curve = visualization.curve.map {
            PointEntity(
                it.x,
                it.y,
            )
        }.toList(),
        realData = visualization.realData.map {
            PointEntity(
                it.x,
                it.y,
            )
        }.toList(),
        userPoint = PointEntity(visualization.userPoint.x ?: 0.0, visualization.userPoint.y ?: 0.0),
    )
}