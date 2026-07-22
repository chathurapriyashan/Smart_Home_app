package com.example.smarthome.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smarthome.screens.FirstFloorScreen
import com.example.smarthome.screens.GroundFloorScreen
import com.example.smarthome.screens.OutdoorScreen
import com.example.smarthome.screens.SettingsScreen


sealed class Screen(
    val route: String,
    val title: String
) {

    object GroundFloor :
        Screen(
            "ground_floor",
            "Ground"
        )


    object FirstFloor :
        Screen(
            "first_floor",
            "First"
        )


    object Outdoor :
        Screen(
            "outdoor",
            "Outdoor"
        )


    object Settings :
        Screen(
            "settings",
            "Settings"
        )
}



@Composable
fun NavigationGraph() {


    val navController = rememberNavController()


    Scaffold(

        bottomBar = {

            BottomNavigation(
                navController
            )

        }

    ) { padding ->


        NavHost(

            navController = navController,

            startDestination = Screen.GroundFloor.route,

            modifier = Modifier.padding(padding)

        ) {


            composable(Screen.GroundFloor.route) {

                GroundFloorScreen()

            }


            composable(Screen.FirstFloor.route) {

                FirstFloorScreen()

            }


            composable(Screen.Outdoor.route) {

                OutdoorScreen()

            }


            composable(Screen.Settings.route) {

                SettingsScreen()

            }

        }

    }

}