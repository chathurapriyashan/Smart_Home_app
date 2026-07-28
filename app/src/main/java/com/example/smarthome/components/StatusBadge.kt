package com.example.smarthome.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthome.model.DeviceStatus
import com.example.smarthome.ui.theme.*


@Composable
fun StatusBadge(
    status: DeviceStatus
) {

    val (text, bgColor) = when (status) {
        DeviceStatus.ON -> {
            "ON" to StatusOnGreen.copy(alpha = 0.15f)
        }
        DeviceStatus.OFF -> {
            "OFF" to StatusOffGray.copy(alpha = 0.2f)
        }
        DeviceStatus.ERROR -> {
            "ERROR" to StatusErrorRed.copy(alpha = 0.15f)
        }
        DeviceStatus.DISCONNECTED -> {
            "OFFLINE" to StatusDisconnected.copy(alpha = 0.2f)
        }
    }

    val textColor = when (status) {
        DeviceStatus.ON -> StatusOnGreen
        DeviceStatus.OFF -> TextSecondary
        DeviceStatus.ERROR -> StatusErrorRed
        DeviceStatus.DISCONNECTED -> TextMuted
    }

    Text(
        text = text,
        color = textColor,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        modifier = Modifier
            .background(
                color = bgColor,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(
                horizontal = 8.dp,
                vertical = 3.dp
            )
    )
}