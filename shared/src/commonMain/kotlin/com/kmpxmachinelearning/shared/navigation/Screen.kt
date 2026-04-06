package com.kmpxmachinelearning.shared.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Salary : Screen()
    @Serializable
    data object Soon1 : Screen()
    @Serializable
    data object Soon2 : Screen()
    @Serializable
    data object Soon3 : Screen()
    @Serializable
    data object Soon4 : Screen()
    @Serializable
    data object Soon5 : Screen()

    @Serializable
    data object HomeGraph : Screen()


}