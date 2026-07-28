package com.example.smarthome.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Power
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthome.components.DashboardSummary
import com.example.smarthome.components.DeviceCard
import com.example.smarthome.components.RoomSection
import com.example.smarthome.model.DeviceStatus
import com.example.smarthome.ui.theme.*
import com.example.smarthome.viewmodel.HomeViewModel


@Composable
fun OutdoorScreen(
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
            text = "Outdoor",
            style = MaterialTheme.typography.headlineMedium,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))


        // ===== OUTDOOR SECTION =====
        RoomSection(
            icon = Icons.Default.Park,
            roomName = "Outdoor Area",
            iconTint = StatusOnGreen,
            iconBgColor = StatusOnGreen.copy(alpha = 0.15f)
        ) {
            // Outdoor Light
            devices
                .filter { it.id == "outdoor_light" }
                .forEach { device ->
                    DeviceCard(
                        device = device,
                        onToggle = { homeViewModel.toggleDevice(device.id) }
                    )
                }

            // CCTV Camera
            devices
                .filter { it.id == "cctv" }
                .forEach { device ->
                    DeviceCard(
                        device = device,
                        onToggle = { homeViewModel.toggleDevice(device.id) }
                    )

                    // Camera status indicator
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = DarkCard
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (device.status == DeviceStatus.ON)
                                            StatusOnGreen.copy(alpha = 0.15f)
                                        else
                                            StatusOffGray.copy(alpha = 0.15f)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FiberManualRecord,
                                    contentDescription = "Status",
                                    tint = if (device.status == DeviceStatus.ON)
                                        StatusOnGreen
                                    else
                                        StatusOffGray,
                                    modifier = Modifier.size(12.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = if (device.status == DeviceStatus.ON)
                                    "Camera Online — Recording"
                                else
                                    "Camera Offline",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (device.status == DeviceStatus.ON)
                                    StatusOnGreen
                                else
                                    TextMuted
                            )
                        }
                    }
                }

            // Main Switch
            devices
                .filter { it.id == "main_switch" }
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