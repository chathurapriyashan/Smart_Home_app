package com.example.smarthome.screens

import androidx.compose.foundation.layout.Arrangement
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
import com.example.smarthome.model.DeviceStatus
import com.example.smarthome.viewmodel.HomeViewModel
import com.example.smarthome.components.DashboardSummary


@Composable
fun OutdoorScreen(
    homeViewModel: HomeViewModel = viewModel()
) {

    val devices = homeViewModel.devices


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),

        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        DashboardSummary(
            devices = devices
        )

        Text(
            text = "🌳 Outdoor"
        )


        // Outdoor Light

        devices
            .filter {
                it.id in listOf(
                    "outdoor_light",
                    "cctv"
                )
            }
            .forEach { device ->

                DeviceCard(
                    device = device,
                    onToggle = {
                        homeViewModel.toggleDevice(device.id)
                    }
                )

                if (device.id == "cctv") {
                    Text(
                        text = if (device.status == DeviceStatus.ON) {
                            "📹 Camera Online"
                        } else {
                            "📹 Camera Offline"
                        }
                    )
                }
            }



    }
}