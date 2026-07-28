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

        // Living Room Light
        val livingLightOn =
            firebaseData["g_living_rm_light"] as? Boolean ?: false

        val livingLightIndex = _devices.indexOfFirst {
            it.id == "living_light"
        }

        if (livingLightIndex != -1) {

            _devices[livingLightIndex] =
                _devices[livingLightIndex].copy(
                    status =
                        if (livingLightOn)
                            DeviceStatus.ON
                        else
                            DeviceStatus.OFF,

                    isEnabled = livingLightOn
                )
        }


        // Kitchen Outlet
        val kitchenOutletOn =
            firebaseData["g_kitchen_rm_switch"] as? Boolean ?: false

        val kitchenIndex = _devices.indexOfFirst {
            it.id == "kitchen_outlet"
        }

        if (kitchenIndex != -1) {

            _devices[kitchenIndex] =
                _devices[kitchenIndex].copy(
                    status =
                        if (kitchenOutletOn)
                            DeviceStatus.ON
                        else
                            DeviceStatus.OFF,

                    isEnabled = kitchenOutletOn
                )
        }
        // Living Room AC

        val livingAcOn =
            firebaseData["g_living_rm_ac"] as? Boolean ?: false


        val livingAcIndex = _devices.indexOfFirst {
            it.id == "living_ac"
        }


        if (livingAcIndex != -1) {

            _devices[livingAcIndex] =
                _devices[livingAcIndex].copy(

                    status =
                        if (livingAcOn)
                            DeviceStatus.ON
                        else
                            DeviceStatus.OFF,

                    isEnabled = livingAcOn
                )
        }
        // Switch 1

        val switch1On =
            firebaseData["g_living_rm_switch"] as? Boolean ?: false


        val switch1Index = _devices.indexOfFirst {
            it.id == "switch_1"
        }


        if (switch1Index != -1) {

            _devices[switch1Index] =
                _devices[switch1Index].copy(

                    status =
                        if (switch1On)
                            DeviceStatus.ON
                        else
                            DeviceStatus.OFF,

                    isEnabled = switch1On
                )
        }


// Switch 2

        val switch2On =
            firebaseData["g_kitchen_rm_switch"] as? Boolean ?: false


        val switch2Index = _devices.indexOfFirst {
            it.id == "switch_2"
        }


        if (switch2Index != -1) {

            _devices[switch2Index] =
                _devices[switch2Index].copy(

                    status =
                        if (switch2On)
                            DeviceStatus.ON
                        else
                            DeviceStatus.OFF,

                    isEnabled = switch2On
                )
        }


// Switch 3

        val switch3On =
            firebaseData["g_play_rm_switch"] as? Boolean ?: false


        val switch3Index = _devices.indexOfFirst {
            it.id == "switch_3"
        }


        if (switch3Index != -1) {

            _devices[switch3Index] =
                _devices[switch3Index].copy(

                    status =
                        if (switch3On)
                            DeviceStatus.ON
                        else
                            DeviceStatus.OFF,

                    isEnabled = switch3On
                )
        }

        // Bedroom Light

        val bedroomLightOn =
            firebaseData["f_bed_rm_light"] as? Boolean ?: false

        val bedroomLightIndex = _devices.indexOfFirst {
            it.id == "bedroom_light"
        }

        if (bedroomLightIndex != -1) {

            _devices[bedroomLightIndex] =
                _devices[bedroomLightIndex].copy(

                    status =
                        if (bedroomLightOn)
                            DeviceStatus.ON
                        else
                            DeviceStatus.OFF,

                    isEnabled = bedroomLightOn
                )
        }

        // Outdoor Light

        val outdoorLightOn =
            firebaseData["outdoor_light"] as? Boolean ?: false

        val outdoorLightIndex = _devices.indexOfFirst {
            it.id == "outdoor_light"
        }

        if (outdoorLightIndex != -1) {

            _devices[outdoorLightIndex] =
                _devices[outdoorLightIndex].copy(

                    status =
                        if (outdoorLightOn)
                            DeviceStatus.ON
                        else
                            DeviceStatus.OFF,

                    isEnabled = outdoorLightOn
                )
        }

        // CCTV Camera

        val cctvOn =
            firebaseData["cctv"] as? Boolean ?: false

        val cctvIndex = _devices.indexOfFirst {
            it.id == "cctv"
        }

        if (cctvIndex != -1) {

            _devices[cctvIndex] =
                _devices[cctvIndex].copy(

                    status =
                        if (cctvOn)
                            DeviceStatus.ON
                        else
                            DeviceStatus.OFF,

                    isEnabled = cctvOn
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
            id = "living_ac",
            name = "Living Room AC",
            type = DeviceType.AC,
            temperature = 24,
            fanSpeed = "Medium"
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
            "kitchen_outlet" -> {

                val currentValue =
                    firebaseData["g_kitchen_rm_switch"] as? Boolean ?: false

                firestoreService.updateBooleanField(
                    "g_kitchen_rm_switch",
                    !currentValue
                )
            }
            "living_ac" -> {

                val currentValue =
                    firebaseData["g_living_rm_ac"] as? Boolean ?: false


                firestoreService.updateBooleanField(
                    "g_living_rm_ac",
                    !currentValue
                )
            }
            "switch_1" -> {

                val current =
                    firebaseData["g_living_rm_switch"] as? Boolean ?: false


                firestoreService.updateBooleanField(
                    "g_living_rm_switch",
                    !current
                )
            }


            "switch_2" -> {

                val current =
                    firebaseData["g_kitchen_rm_switch"] as? Boolean ?: false


                firestoreService.updateBooleanField(
                    "g_kitchen_rm_switch",
                    !current
                )
            }


            "switch_3" -> {

                val current =
                    firebaseData["g_play_rm_switch"] as? Boolean ?: false


                firestoreService.updateBooleanField(
                    "g_play_rm_switch",
                    !current
                )
            }

            "bedroom_light" -> {

                val currentValue =
                    firebaseData["f_bed_rm_light"] as? Boolean ?: false

                firestoreService.updateBooleanField(
                    "f_bed_rm_light",
                    !currentValue
                )
            }
            "outdoor_light" -> {

                val currentValue =
                    firebaseData["outdoor_light"] as? Boolean ?: false

                firestoreService.updateBooleanField(
                    "outdoor_light",
                    !currentValue
                )
            }
            "cctv" -> {

                val currentValue =
                    firebaseData["cctv"] as? Boolean ?: false

                firestoreService.updateBooleanField(
                    "cctv",
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