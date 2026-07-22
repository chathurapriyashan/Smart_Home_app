package com.example.smarthome.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController


data class BottomNavItem(
    val screen: Screen,
    val icon: ImageVector
)



@Composable
fun BottomNavigation(
    navController: NavHostController
) {


    val items = listOf(

        BottomNavItem(
            Screen.GroundFloor,
            Icons.Default.Home
        ),

        BottomNavItem(
            Screen.FirstFloor,
            Icons.Default.Bed
        ),

        BottomNavItem(
            Screen.Outdoor,
            Icons.Default.WbSunny
        ),

        BottomNavItem(
            Screen.Settings,
            Icons.Default.Settings
        )

    )



    NavigationBar {


        items.forEach { item ->


            NavigationBarItem(

                selected = false,

                onClick = {

                    navController.navigate(
                        item.screen.route
                    )

                },


                icon = {

                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.screen.title
                    )

                },


                label = {

                    Text(
                        item.screen.title
                    )

                }

            )


        }


    }

}