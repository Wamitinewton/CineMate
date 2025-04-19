package com.newton.cinemate.navigation


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import com.newton.navigation.*
import com.newton.shared_ui.sharedComponents.*

@Composable
fun NotchedBottomNavigationBar(navController: NavHostController, currentDestination: NavDestination?) {
    val density = LocalDensity.current
    val notchSize = 56.dp
    val notchSizePx = with(density) { notchSize.toPx() }
    val barHeight = 64.dp
    val cornerRadius = 28.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 6.dp)
            .height(barHeight)
    ) {
        val customShape = remember {
            object : Shape {
                override fun createOutline(
                    size: Size,
                    layoutDirection: LayoutDirection,
                    density: Density
                ): Outline {
                    val cornerRadiusPx = with(density) { cornerRadius.toPx() }

                    val path = Path().apply {
                        moveTo(cornerRadiusPx, 0f)

                        lineTo(size.width / 2 - notchSizePx / 2, 0f)

                        quadraticTo(
                            size.width / 2 - notchSizePx / 4, 0f,
                            size.width / 2, notchSizePx / 2
                        )

                        quadraticTo(
                            size.width / 2 + notchSizePx / 4, 0f,
                            size.width / 2 + notchSizePx / 2, 0f
                        )

                        lineTo(size.width - cornerRadiusPx, 0f)

                        quadraticTo(
                            size.width, 0f,
                            size.width, cornerRadiusPx
                        )

                        lineTo(size.width, size.height - cornerRadiusPx)

                        quadraticTo(
                            size.width, size.height,
                            size.width - cornerRadiusPx, size.height
                        )

                        lineTo(cornerRadiusPx, size.height)

                        quadraticTo(
                            0f, size.height,
                            0f, size.height - cornerRadiusPx
                        )

                        lineTo(0f, cornerRadiusPx)

                        quadraticTo(
                            0f, 0f,
                            cornerRadiusPx, 0f
                        )

                        close()
                    }
                    return Outline.Generic(path)
                }
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .shadow(
                    elevation = 16.dp,
                    shape = customShape,
                    spotColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
                )
                .clip(customShape),
            shape = customShape,
            tonalElevation = 2.dp,
            shadowElevation = 6.dp,
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val leftSideDestinations = listOf(
                            NavigationSubgraphRoutes.Trending,
                            NavigationSubgraphRoutes.Movies
                        ).mapNotNull { route ->
                            bottomNavigationDestinations.find { it.route == route.route }
                        }

                        leftSideDestinations.forEach { destination ->
                            val selected = currentDestination?.hierarchy?.any {
                                it.route == destination.route
                            } == true

                            NavItem(
                                isSelected = selected,
                                destination = destination,
                                onClick = {
                                    if (!selected) {
                                        navController.navigate(destination.route) {
                                            launchSingleTop = true
                                            popUpTo(NavigationRoutes.HomeScreenRoute.routes)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(notchSize))

                Box(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val rightSideDestinations = listOf(
                            NavigationSubgraphRoutes.Shows,
                            NavigationSubgraphRoutes.Search,
                        ).mapNotNull { route ->
                            bottomNavigationDestinations.find { it.route == route.route }
                        }

                        rightSideDestinations.forEach { destination ->
                            val selected = currentDestination?.hierarchy?.any {
                                it.route == destination.route
                            } == true

                            NavItem(
                                isSelected = selected,
                                destination = destination,
                                onClick = {
                                    if (!selected) {
                                        navController.navigate(destination.route) {
                                            launchSingleTop = true
                                            popUpTo(NavigationRoutes.HomeScreenRoute.routes)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        FloatingSearchButton(
            modifier = Modifier
                .size(notchSize)
                .align(Alignment.TopCenter)
                .offset(y = (-4).dp),
            onClick = {

            }
        )
    }
}
@Composable
fun NavItem(
    isSelected: Boolean,
    destination: Screens,
    onClick: () -> Unit
) {
    val iconColor = if (isSelected) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    }

    val textColor = if (isSelected) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    }

    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {


        Icon(
            imageVector = if (isSelected) {
                destination.selectedIcon
            } else {
                destination.unSelectedIcon
            },
            contentDescription = destination.title,
            tint = iconColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = destination.title,
            color = textColor,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}