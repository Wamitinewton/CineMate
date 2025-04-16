package com.newton.cinemate.navigation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import com.newton.navigation.*
import com.newton.shared_ui.theme.*

@Composable
fun BottomNavigationBar(navController: NavHostController, currentDestination: NavDestination?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 3.dp)
    ) {
        Surface(
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(32.dp),
                    spotColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                )
                .clip(RoundedCornerShape(32.dp))
                .background(brush = backgroundGradient),
            tonalElevation = 4.dp,
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                bottomNavigationDestinations.forEach { destination ->
                    val selected = currentDestination?.hierarchy?.any {
                        it.route == destination.route
                    } ?: false
                    BottomNavItem(
                        isSelected = selected,
                        destination = destination,
                        onClick = {
                            if (!selected) {
                                destination.route.let {
                                    navController.navigate(it) {
                                        launchSingleTop = true
                                        popUpTo(NavigationRoutes.HomeScreenRoute.routes)
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.BottomNavItem(
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
            .weight(1f)
            .fillMaxHeight()
            .clickable(onClick = onClick),
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
            modifier = Modifier.size(26.dp)
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = destination.title,
            color = textColor,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}