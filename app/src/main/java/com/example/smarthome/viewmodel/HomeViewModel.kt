package com.example.smarthome.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.smarthome.model.Device
import com.example.smarthome.model.DeviceStatus
import com.example.smarthome.model.DeviceType


class HomeViewModel : ViewModel() {


    // All smart home devices
    private val _devices = mutableStateListOf(

        // Ground Floor
        Device(
            id = "living_light",
            name = "Living Room Light",
            type = DeviceType.LIGHT
        ),

        Device(
            id = "kitchen_outlet",
            name = "Kitchen Outlet",
            type = DeviceType.SWITCH
        ),

        Device(
            id = "switch_1",
            name = "Switch 1",
            type = DeviceType.SWITCH
        ),

        Device(
            id = "switch_2",
            name = "Switch 2",
            type = DeviceType.SWITCH
        ),

        Device(
            id = "switch_3",
            name = "Switch 3",
            type = DeviceType.SWITCH
        ),

        Device(
            id = "living_ac",
            name = "Living Room AC",
            type = DeviceType.AC,
            temperature = 24,
            fanSpeed = "Medium"
        ),


        // First Floor

        Device(
            id = "bedroom_light",
            name = "Bedroom Light",
            type = DeviceType.LIGHT
        ),

        Device(
            id = "iron",
            name = "Bedroom Iron",
            type = DeviceType.IRON
        ),

        Device(
            id = "bedroom_ac",
            name = "Bedroom AC",
            type = DeviceType.AC,
            temperature = 22,
            fanSpeed = "Low"
        ),


        // Outdoor

        Device(
            id = "outdoor_light",
            name = "Outdoor Light",
            type = DeviceType.LIGHT
        ),

        Device(
            id = "cctv",
            name = "CCTV Camera",
            type = DeviceType.CAMERA
        )

    )


    // Expose devices to UI
    val devices: List<Device>
        get() = _devices



    // Change device ON/OFF state
    fun toggleDevice(deviceId: String) {

        val device = _devices.find {
            it.id == deviceId
        }

        device?.let {

            if (it.status == DeviceStatus.ON) {

                it.status = DeviceStatus.OFF
                it.isEnabled = false

            } else {

                it.status = DeviceStatus.ON
                it.isEnabled = true
            }
        }
    }


    // Find specific device
    fun getDevice(id: String): Device? {

        return _devices.find {
            it.id == id
        }

    }

}