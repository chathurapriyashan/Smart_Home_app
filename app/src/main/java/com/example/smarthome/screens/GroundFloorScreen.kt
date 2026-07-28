package com.example.smarthome.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Living
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthome.components.DashboardSummary
import com.example.smarthome.components.DeviceCard
import com.example.smarthome.components.RoomSection
import com.example.smarthome.ui.theme.*
import com.example.smarthome.viewmodel.HomeViewModel


@Composable
fun GroundFloorScreen(
    homeViewModel: HomeViewModel = viewModel()
) {

    val devices = homeViewModel.devices


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        DashboardSummary(devices = devices)

        Text(
            text = "Ground Floor",
            style = MaterialTheme.typography.headlineMedium,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))


        // ===== LIVING ROOM =====
        RoomSection(
            icon = Icons.Default.Living,
            roomName = "Living Room"
        ) {
            devices
                .filter {
                    it.id in listOf(
                        "living_light",
                        "living_ac",
                        "living_switch"
                    )
                }
                .forEach { device ->
                    DeviceCard(
                        device = device,
                        onToggle = { homeViewModel.toggleDevice(device.id) }
                    )
                }
        }


        // ===== KITCHEN =====
        RoomSection(
            icon = Icons.Default.Kitchen,
            roomName = "Kitchen",
            iconTint = AccentAmber,
            iconBgColor = AccentAmber.copy(alpha = 0.15f)
        ) {
            devices
                .filter {
                    it.id in listOf(
                        "kitchen_light",
                        "kitchen_outlet"
                    )
                }
                .forEach { device ->
                    DeviceCard(
                        device = device,
                        onToggle = { homeViewModel.toggleDevice(device.id) }
                    )
                }
        }


        // ===== PLAYING ROOM =====
        RoomSection(
            icon = Icons.Default.SportsEsports,
            roomName = "Playing Room",
            iconTint = AccentPurple,
            iconBgColor = AccentPurple.copy(alpha = 0.15f)
        ) {
            devices
                .filter {
                    it.id in listOf(
                        "playing_rm_light",
                        "playing_rm_switch"
                    )
                }
                .forEach { device ->
                    DeviceCard(
                        device = device,
                        onToggle = { homeViewModel.toggleDevice(device.id) }
                    )
                }
        }


        Spacer(modifier = Modifier.height(16.dp))
    }
}
