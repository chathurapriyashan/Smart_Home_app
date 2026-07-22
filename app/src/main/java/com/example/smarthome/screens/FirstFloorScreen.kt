package com.example.smarthome.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthome.components.DeviceCard
import com.example.smarthome.utils.startTimer
import com.example.smarthome.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import com.example.smarthome.components.DashboardSummary
import com.example.smarthome.components.TimerCard


@Composable
fun FirstFloorScreen(
    homeViewModel: HomeViewModel = viewModel()
) {

    val devices = homeViewModel.devices

    var timerMinutes by remember {
        mutableStateOf("")
    }


    var remainingTime by remember {
        mutableStateOf("00:00")
    }


    val coroutineScope = rememberCoroutineScope()


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
            text = "🛏 First Floor"
        )


        // Bedroom Light

        devices
            .filter { it.id == "bedroom_light" }
            .forEach { device ->

                DeviceCard(
                    device = device,
                    onToggle = {
                        homeViewModel.toggleDevice(device.id)
                    }
                )

            }


        TimerCard()
        // Iron Card

        devices
            .filter { it.id == "iron" }
            .forEach { device ->

                DeviceCard(
                    device = device,
                    onToggle = {
                        homeViewModel.toggleDevice(device.id)
                    }
                )

            }



        Text(
            text = "🔥 Iron Timer"
        )


        TextField(
            value = timerMinutes,
            onValueChange = {
                timerMinutes = it
            },
            label = {
                Text("Enter Minutes")
            }
        )


        Button(
            onClick = {

                val minutes =
                    timerMinutes.toIntOrNull()


                if(minutes != null) {


                    coroutineScope.launch {


                        startTimer(

                            minutes = minutes,

                            onTick = {
                                remainingTime = it
                            },


                            onFinish = {

                                remainingTime = "00:00"


                                // Turn iron OFF automatically
                                homeViewModel
                                    .getDevice("iron")
                                    ?.let {

                                        it.status =
                                            com.example.smarthome.model.DeviceStatus.OFF

                                        it.isEnabled = false

                                    }

                            }
                        )

                    }

                }

            }

        ) {

            Text("START TIMER")

        }



        Text(
            text = "Remaining Time: $remainingTime"
        )




        // Bedroom AC

        devices
            .filter { it.id == "bedroom_ac" }
            .forEach { device ->


                DeviceCard(
                    device = device,
                    onToggle = {
                        homeViewModel.toggleDevice(device.id)
                    }
                )

            }

    }
}