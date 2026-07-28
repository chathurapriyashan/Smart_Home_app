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
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthome.components.DashboardSummary
import com.example.smarthome.components.DeviceCard
import com.example.smarthome.components.RoomSection
import com.example.smarthome.components.TimerCard
import com.example.smarthome.model.DeviceStatus
import com.example.smarthome.ui.theme.*
import com.example.smarthome.utils.startTimer
import com.example.smarthome.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@Composable
fun FirstFloorScreen(
    homeViewModel: HomeViewModel = viewModel()
) {

    val devices = homeViewModel.devices

    var remainingTime by remember { mutableStateOf("00:00") }
    val coroutineScope = rememberCoroutineScope()
    var timerJob by remember { mutableStateOf<Job?>(null) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        // Dashboard summary
        DashboardSummary(devices = devices)

        // Screen title
        Text(
            text = "First Floor",
            style = MaterialTheme.typography.headlineMedium,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))


        // ===== BEDROOM SECTION =====
        RoomSection(
            icon = Icons.Default.Bed,
            roomName = "Bedroom"
        ) {
            // Bedroom Light
            devices
                .filter { it.id == "bed_rm_light" }
                .forEach { device ->
                    DeviceCard(
                        device = device,
                        onToggle = { homeViewModel.toggleDevice(device.id) }
                    )
                }

            // Bedroom AC
            devices
                .filter { it.id == "bed_rm_ac" }
                .forEach { device ->
                    DeviceCard(
                        device = device,
                        onToggle = { homeViewModel.toggleDevice(device.id) }
                    )
                }

            // Bedroom Switch
            devices
                .filter { it.id == "bed_rm_switch" }
                .forEach { device ->
                    DeviceCard(
                        device = device,
                        onToggle = { homeViewModel.toggleDevice(device.id) }
                    )
                }
        }


        // ===== CLOTHING ROOM SECTION =====
        RoomSection(
            icon = Icons.Default.Checkroom,
            roomName = "Clothing Room",
            iconTint = AccentAmber,
            iconBgColor = AccentAmber.copy(alpha = 0.15f)
        ) {
            // Clothing Room Light
            devices
                .filter { it.id == "cloth_rm_light" }
                .forEach { device ->
                    DeviceCard(
                        device = device,
                        onToggle = { homeViewModel.toggleDevice(device.id) }
                    )
                }

            // Clothing Room Iron
            devices
                .filter { it.id == "cloth_rm_iron" }
                .forEach { device ->
                    DeviceCard(
                        device = device,
                        onToggle = { homeViewModel.toggleDevice(device.id) }
                    )
                }

            // Iron Safety Timer
            TimerCard(
                title = "Iron Safety Timer",
                remainingTime = remainingTime,
                onStartTimer = { minutes ->
                    timerJob?.cancel()
                    timerJob = coroutineScope.launch {
                        startTimer(
                            minutes = minutes,
                            onTick = { remainingTime = it },
                            onFinish = {
                                remainingTime = "00:00"
                                // Turn iron OFF automatically
                                homeViewModel.getDevice("cloth_rm_iron")?.let {
                                    it.status = DeviceStatus.OFF
                                    it.isEnabled = false
                                }
                                homeViewModel.toggleDevice("cloth_rm_iron")
                            }
                        )
                    }
                },
                onStopTimer = {
                    timerJob?.cancel()
                    remainingTime = "00:00"
                }
            )

            // Clothing Room Switch
            devices
                .filter { it.id == "cloth_rm_switch" }
                .forEach { device ->
                    DeviceCard(
                        device = device,
                        onToggle = { homeViewModel.toggleDevice(device.id) }
                    )
                }
        }


        // ===== GUEST ROOM SECTION =====
        RoomSection(
            icon = Icons.Default.MeetingRoom,
            roomName = "Guest Room",
            iconTint = AccentPurple,
            iconBgColor = AccentPurple.copy(alpha = 0.15f)
        ) {
            // Guest Room Light
            devices
                .filter { it.id == "guest_rm_light" }
                .forEach { device ->
                    DeviceCard(
                        device = device,
                        onToggle = { homeViewModel.toggleDevice(device.id) }
                    )
                }

            // Guest Room Switch 1
            devices
                .filter { it.id == "guest_rm_switch_1" }
                .forEach { device ->
                    DeviceCard(
                        device = device,
                        onToggle = { homeViewModel.toggleDevice(device.id) }
                    )
                }

            // Guest Room Switch 2
            devices
                .filter { it.id == "guest_rm_switch_2" }
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