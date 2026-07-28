package com.example.smarthome.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthome.components.DeviceCard
import com.example.smarthome.viewmodel.HomeViewModel
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import com.example.smarthome.components.DashboardSummary
import com.example.smarthome.components.SwitchCard



@Composable
fun GroundFloorScreen(
    homeViewModel: HomeViewModel = viewModel()
) {

    val devices = homeViewModel.devices


    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(16.dp)

    ) {
        DashboardSummary(
            devices = devices
        )

        Text(
            text = "🏠 Ground Floor",
            modifier = Modifier.padding(bottom = 16.dp)
        )


        devices
            .filter {
                it.id in listOf(
                    "living_light",
                    "kitchen_outlet",
                    "living_ac",
                    "switch_1",
                    "switch_2",
                    "switch_3"

                )
            }
            .forEach { device ->


                DeviceCard(
                    device = device,
                    onToggle = {
                        homeViewModel.toggleDevice(device.id)
                    }
                )

            }
        SwitchCard()
    }
    }
