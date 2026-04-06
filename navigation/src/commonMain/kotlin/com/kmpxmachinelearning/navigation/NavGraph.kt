package com.kmpxmachinelearning.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kmpxmachinelearning.home.HomeGraphScreen
import com.kmpxmachinelearning.shared.navigation.Screen

@Composable
fun SetupNavGraph(startDestination: Screen = Screen.Salary) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.HomeGraph> {
            HomeGraphScreen(
                navigateToSoon3 = {
                    navController.navigate(Screen.Soon3)
                },
                navigateToSoon4 = {
                    navController.navigate(Screen.Soon4)
                },
                navigateToSoon5 = {
                    navController.navigate(Screen.Soon5)
                },
            )
        }
    }
}