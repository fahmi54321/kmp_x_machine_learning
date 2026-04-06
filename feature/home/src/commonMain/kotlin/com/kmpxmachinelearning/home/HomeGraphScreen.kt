package com.kmpxmachinelearning.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kmpxmachinelearning.home.component.BottomBar
import com.kmpxmachinelearning.home.component.CustomDrawer
import com.kmpxmachinelearning.home.domain.BottomBarDestination
import com.kmpxmachinelearning.home.domain.CustomDrawerState
import com.kmpxmachinelearning.home.domain.isOpened
import com.kmpxmachinelearning.home.domain.oppsite
import com.kmpxmachinelearning.salary.presentation.SalaryScreen
import com.kmpxmachinelearning.shared.FontFamily
import com.kmpxmachinelearning.shared.FontSize
import com.kmpxmachinelearning.shared.GlassBorder
import com.kmpxmachinelearning.shared.GradientPrimary
import com.kmpxmachinelearning.shared.GlassSurface
import com.kmpxmachinelearning.shared.Resources
import com.kmpxmachinelearning.shared.navigation.Screen
import com.kmpxmachinelearning.shared.util.getScreenWidth
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeGraphScreen(
    navigateToSoon3: () -> Unit,
    navigateToSoon4: () -> Unit,
    navigateToSoon5: () -> Unit,
) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState()

    val selectedDestination by remember {
        derivedStateOf {
            val route = currentRoute.value?.destination?.route.toString()
            when {
                route.contains(BottomBarDestination.Salary.screen.toString()) -> BottomBarDestination.Salary
                route.contains(BottomBarDestination.Soon1.screen.toString()) -> BottomBarDestination.Soon1
                route.contains(BottomBarDestination.Soon2.screen.toString()) -> BottomBarDestination.Soon2
                else -> BottomBarDestination.Salary
            }
        }
    }

    val screenWidth = remember { getScreenWidth() }
    var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }

    val offsetValue by remember { derivedStateOf { (screenWidth / 1.5).dp } }

    val animatedOffset by animateDpAsState(
        targetValue = if (drawerState.isOpened()) offsetValue else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val animatedScale by animateFloatAsState(
        targetValue = if (drawerState.isOpened()) 0.9f else 1f,
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )

    val animatedRadius by animateDpAsState(
        targetValue = if (drawerState.isOpened()) 20.dp else 0.dp
    )

    val overlay by animateColorAsState(
        targetValue = if (drawerState.isOpened())
            Color.White.copy(alpha = 0.05f)
        else Color.Transparent
    )

    val drawerAlpha by animateFloatAsState(
        targetValue = if (drawerState.isOpened()) 1f else 0f,
        animationSpec = tween(300)
    )

    val drawerOffset by animateDpAsState(
        targetValue = if (drawerState.isOpened()) 0.dp else (-40).dp,
        animationSpec = tween(300)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GradientPrimary)
            .background(overlay)
            .systemBarsPadding()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(drawerAlpha)
                .offset(x = drawerOffset)
                .then(
                    if (drawerState.isOpened() == false) Modifier.pointerInput(Unit) {}
                    else Modifier
                )
        ) {
            CustomDrawer(
                onSoon3Click = navigateToSoon3,
                onSoon4Click = navigateToSoon4,
                onSoon5Click = navigateToSoon5,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = animatedOffset)
                .scale(animatedScale)
                .clip(RoundedCornerShape(animatedRadius))
                .background(GlassSurface)
                .border(1.dp, GlassBorder, RoundedCornerShape(animatedRadius))
        ) {

            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    CenterAlignedTopAppBar(
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.05f)),

                        title = {
                            AnimatedContent(targetState = selectedDestination) {
                                Text(
                                    text = it.title,
                                    fontFamily = FontFamily.bebasNeueFont(),
                                    fontSize = FontSize.LARGE,
                                    color = Color.White
                                )
                            }
                        },

                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    drawerState = drawerState.oppsite()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(
                                        if (drawerState.isOpened())
                                            Resources.Icon.Close
                                        else Resources.Icon.Menu
                                    ),
                                    contentDescription = null,
                                    tint = Color.White.copy(alpha = 0.85f)
                                )
                            }
                        },

                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = Color.Transparent,
                        )
                    )
                }
            ) { padding ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = padding.calculateTopPadding(),
                            bottom = padding.calculateBottomPadding()
                        )
                ) {

                    NavHost(
                        modifier = Modifier.weight(1f),
                        navController = navController,
                        startDestination = Screen.Salary,
                    ) {
                        composable<Screen.Salary>() { SalaryScreen() }
                        composable<Screen.Soon1>() { Scaffold {} }
                        composable<Screen.Soon2>() { Scaffold {} }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        BottomBar(
                            modifier = Modifier.align(Alignment.Center),
                            selected = selectedDestination,
                            onSelect = { destination ->
                                navController.navigate(destination.screen) {
                                    launchSingleTop = true
                                    popUpTo<Screen.Salary> {
                                        saveState = true
                                        inclusive = false
                                    }
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}