package com.kmpxmachinelearning.home.domain

import com.kmpxmachinelearning.shared.Resources
import com.kmpxmachinelearning.shared.navigation.Screen
import org.jetbrains.compose.resources.DrawableResource

enum class BottomBarDestination(
    val icon: DrawableResource,
    val title: String,
    val screen: Screen
) {
    Salary(
        icon = Resources.Icon.Home,
        title = "Salary",
        screen = Screen.Salary
    ),
    Soon1(
        icon = Resources.Icon.Book,
        title = "Soon 1",
        screen = Screen.Soon1
    ),

    Soon2(
        icon = Resources.Icon.Unlock,
        title = "Soon 2",
        screen = Screen.Soon2
    ),
}