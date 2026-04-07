package com.kmpxmachinelearning.salary.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SalaryModel(
    @SerialName("input")
    val input: InputModel,
    @SerialName("insight")
    val insight: InsightModel,
    @SerialName("prediction")
    val prediction: PredictionModel,
    @SerialName("visualization")
    val visualization: VisualizationModel,
)

@Serializable
data class InsightModel(
    @SerialName("category")
    val category: String,
    @SerialName("confidence_note")
    val confidenceNote: String,
    @SerialName("recommendation")
    val recommendation: String,
)

@Serializable
data class InputModel(
    @SerialName("position_level")
    val positionLevel: Double,
)

@Serializable
data class PredictionModel(
    @SerialName("currency")
    val currency: String,
    @SerialName("formatted")
    val formatted: String,
    @SerialName("salary")
    val salary: Double,
)

@Serializable
data class VisualizationModel(
    @SerialName("curve")
    val curve: List<PointModel>,

    @SerialName("real_data")
    val realData: List<PointModel>,

    @SerialName("user_point")
    val userPoint: PointModel,
)

@Serializable
data class PointModel(
    @SerialName("x")
    val x: Double,
    @SerialName("y")
    val y: Double,
)