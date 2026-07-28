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


    // Helper to update a single device from Firebase
    private fun syncDevice(deviceId: String, firebaseField: String) {
        val isOn = firebaseData[firebaseField] as? Boolean ?: false
        val index = _devices.indexOfFirst { it.id == deviceId }
        if (index != -1) {
            _devices[index] = _devices[index].copy(
                status = if (isOn) DeviceStatus.ON else DeviceStatus.OFF,
                isEnabled = isOn
            )
        }
    }


    private fun updateDevicesFromFirebase() {

        // ===== Ground Floor =====
        syncDevice("living_light", "g_living_rm_light")
        syncDevice("kitchen_light", "g_kitchen_rm_light")
        syncDevice("kitchen_outlet", "g_kitchen_rm_switch")
        syncDevice("living_ac", "g_living_rm_ac")
        syncDevice("living_switch", "g_living_rm_switch")
        syncDevice("kitchen_switch", "g_kitchen_rm_switch")
        syncDevice("playing_rm_light", "g_playing_rm_light")
        syncDevice("playing_rm_switch", "g_play_rm_switch")

        // ===== First Floor =====
        // Bedroom
        syncDevice("bed_rm_light", "f_bed_rm_light")
        syncDevice("bed_rm_ac", "f_bed_rm_ac")
        syncDevice("bed_rm_switch", "f_bed_rm_switch")

        // Clothing Room
        syncDevice("cloth_rm_light", "f_cloth_rm_light")
        syncDevice("cloth_rm_iron", "f_cloth_rm_iron")
        syncDevice("cloth_rm_switch", "f_cloth_rm_switch")

        // Guest Room
        syncDevice("guest_rm_light", "f_guest_rm_light")
        syncDevice("guest_rm_switch_1", "f_guest_rm_switch_1")
        syncDevice("guest_rm_switch_2", "f_guest_rm_switch_2")

        // ===== Outdoor =====
        syncDevice("outdoor_light", "outdoor_light")
        syncDevice("cctv", "cctv")
        syncDevice("main_switch", "main_switch")
    }


    // All smart home devices
    private val _devices = mutableStateListOf(

        // ===== Ground Floor =====
        Device(
            id = "living_light",
            name = "Living Room Light",
            type = DeviceType.LIGHT
        ),
        Device(
            id = "kitchen_light",
            name = "Kitchen Light",
            type = DeviceType.LIGHT
        ),
        Device(
            id = "kitchen_outlet",
            name = "Kitchen Switch",
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
            id = "living_switch",
            name = "Living Room Switch",
            type = DeviceType.SWITCH
        ),
        Device(
            id = "kitchen_switch",
            name = "Kitchen Room Switch",
            type = DeviceType.SWITCH
        ),
        Device(
            id = "playing_rm_light",
            name = "Playing Room Light",
            type = DeviceType.LIGHT
        ),
        Device(
            id = "playing_rm_switch",
            name = "Playing Room Switch",
            type = DeviceType.SWITCH
        ),

        // ===== First Floor — Bedroom =====
        Device(
            id = "bed_rm_light",
            name = "Bedroom Light",
            type = DeviceType.LIGHT
        ),
        Device(
            id = "bed_rm_ac",
            name = "Bedroom AC",
            type = DeviceType.AC,
            temperature = 22,
            fanSpeed = "Low"
        ),
        Device(
            id = "bed_rm_switch",
            name = "Bedroom Switch",
            type = DeviceType.SWITCH
        ),

        // ===== First Floor — Clothing Room =====
        Device(
            id = "cloth_rm_light",
            name = "Clothing Room Light",
            type = DeviceType.LIGHT
        ),
        Device(
            id = "cloth_rm_iron",
            name = "Clothing Room Iron",
            type = DeviceType.IRON
        ),
        Device(
            id = "cloth_rm_switch",
            name = "Clothing Room Switch",
            type = DeviceType.SWITCH
        ),

        // ===== First Floor — Guest Room =====
        Device(
            id = "guest_rm_light",
            name = "Guest Room Light",
            type = DeviceType.LIGHT
        ),
        Device(
            id = "guest_rm_switch_1",
            name = "Guest Room Switch 1",
            type = DeviceType.SWITCH
        ),
        Device(
            id = "guest_rm_switch_2",
            name = "Guest Room Switch 2",
            type = DeviceType.SWITCH
        ),

        // ===== Outdoor =====
        Device(
            id = "outdoor_light",
            name = "Outdoor Light",
            type = DeviceType.LIGHT
        ),
        Device(
            id = "cctv",
            name = "CCTV Camera",
            type = DeviceType.CAMERA
        ),
        Device(
            id = "main_switch",
            name = "Main Switch",
            type = DeviceType.SWITCH
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


    // Firebase field mapping for each device
    private val deviceFieldMap = mapOf(
        // Ground Floor
        "living_light" to "g_living_rm_light",
        "kitchen_light" to "g_kitchen_rm_light",
        "kitchen_outlet" to "g_kitchen_rm_switch",
        "living_ac" to "g_living_rm_ac",
        "living_switch" to "g_living_rm_switch",
        "kitchen_switch" to "g_kitchen_rm_switch",
        "playing_rm_light" to "g_playing_rm_light",
        "playing_rm_switch" to "g_play_rm_switch",
        // First Floor — Bedroom
        "bed_rm_light" to "f_bed_rm_light",
        "bed_rm_ac" to "f_bed_rm_ac",
        "bed_rm_switch" to "f_bed_rm_switch",
        // First Floor — Clothing Room
        "cloth_rm_light" to "f_cloth_rm_light",
        "cloth_rm_iron" to "f_cloth_rm_iron",
        "cloth_rm_switch" to "f_cloth_rm_switch",
        // First Floor — Guest Room
        "guest_rm_light" to "f_guest_rm_light",
        "guest_rm_switch_1" to "f_guest_rm_switch_1",
        "guest_rm_switch_2" to "f_guest_rm_switch_2",
        // Outdoor
        "outdoor_light" to "outdoor_light",
        "cctv" to "cctv",
        "main_switch" to "main_switch"
    )


    // Change device ON/OFF state
    fun toggleDevice(deviceId: String) {
        val firebaseField = deviceFieldMap[deviceId]
        if (firebaseField != null) {
            val currentValue = firebaseData[firebaseField] as? Boolean ?: false
            firestoreService.updateBooleanField(firebaseField, !currentValue)
        } else {
            Log.w("HomeViewModel", "No Firebase field mapping for device: $deviceId")
        }
    }


    // Find specific device
    fun getDevice(id: String): Device? {

        return _devices.find {
            it.id == id
        }

    }

}