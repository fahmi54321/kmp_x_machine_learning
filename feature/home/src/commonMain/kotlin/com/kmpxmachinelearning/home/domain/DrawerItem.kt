package com.kmpxmachinelearning.home.domain

import com.kmpxmachinelearning.shared.Resources
import org.jetbrains.compose.resources.DrawableResource

enum class DrawerItem(
    val title: String,
    val icon: DrawableResource
) {
    Soon1(
        title = "Soon1",
        icon = Resources.Icon.Person
    ),
    Soon2(
        title = "Soon2",
        icon = Resources.Icon.Book
    ),
    Soon3(
        title = "Soon3",
        icon = Resources.Icon.MapPin
    ),
    Soon4(
        title = "Soon4",
        icon = Resources.Icon.Edit
    ),
    Soon5(
        title = "Soon5",
        icon = Resources.Icon.SignOut
    ),
    Soon6(
        title = "Soon6",
        icon = Resources.Icon.Unlock
    )
}