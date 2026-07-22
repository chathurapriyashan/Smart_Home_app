package com.example.smarthome.model

data class Device(
    val id: String,
    val name: String,
    val type: DeviceType,
    var status: DeviceStatus = DeviceStatus.OFF,
    var isEnabled: Boolean = false,
    val temperature: Int? = null,
    val fanSpeed: String? = null
)
