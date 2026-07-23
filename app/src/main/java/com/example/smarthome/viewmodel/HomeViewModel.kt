package com.example.smarthome.viewmodel

import android.util.Log
import com.example.smarthome.firebase.FirestoreService
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.smarthome.model.Device
import com.example.smarthome.model.DeviceStatus
import com.example.smarthome.model.DeviceType


class HomeViewModel : ViewModel() {

    private val firestoreService = FirestoreService()

    private var firebaseData: Map<String, Any> = emptyMap()


    private fun updateDevicesFromFirebase() {

        val isOn =
            firebaseData["g_living_rm_light"] as? Boolean ?: false


        val index = _devices.indexOfFirst {
            it.id == "living_light"
        }


        if (index != -1) {

            _devices[index] =
                _devices[index].copy(

                    status =
                        if (isOn)
                            DeviceStatus.ON
                        else
                            DeviceStatus.OFF,

                    isEnabled = isOn
                )
        }
    }
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

    init {

        firestoreService.listenToDevices { data ->

            firebaseData = data

            updateDevicesFromFirebase()

            Log.d("HomeViewModel", "Firebase Data: $firebaseData")

        }

    }



    // Change device ON/OFF state
    fun toggleDevice(deviceId: String) {

        when (deviceId) {

            "living_light" -> {

                val currentValue =
                    firebaseData["g_living_rm_light"] as? Boolean ?: false

                firestoreService.updateBooleanField(
                    "g_living_rm_light",
                    !currentValue
                )
            }

            else -> {
                // Other devices will be added later
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