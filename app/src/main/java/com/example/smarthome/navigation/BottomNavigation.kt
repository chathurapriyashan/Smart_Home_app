package com.example.smarthome.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.smarthome.ui.theme.*


data class BottomNavItem(
    val screen: Screen,
    val icon: ImageVector
)



@Composable
fun BottomNavigation(
    navController: NavHostController
) {

    val items = listOf(
        BottomNavItem(Screen.GroundFloor, Icons.Default.Home),
        BottomNavItem(Screen.FirstFloor, Icons.Default.Bed),
        BottomNavItem(Screen.Outdoor, Icons.Default.WbSunny),
        BottomNavItem(Screen.Settings, Icons.Default.Settings)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    NavigationBar(
        containerColor = NavBarBackground,
        contentColor = NavBarSelected
    ) {

        items.forEach { item ->

            val isSelected = currentRoute == item.screen.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.screen.route) {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.screen.title
                    )
                },
                label = {
                    Text(
                        text = item.screen.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NavBarSelected,
                    selectedTextColor = NavBarSelected,
                    unselectedIconColor = NavBarUnselected,
                    unselectedTextColor = NavBarUnselected,
                    indicatorColor = NavBarSelected.copy(alpha = 0.12f)
                )
            )
        }
    }
}