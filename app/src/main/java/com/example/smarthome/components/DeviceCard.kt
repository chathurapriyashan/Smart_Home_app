package com.example.smarthome.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthome.model.Device
import com.example.smarthome.model.DeviceStatus
import com.example.smarthome.model.DeviceType
import com.example.smarthome.ui.theme.*


@Composable
fun DeviceCard(
    device: Device,
    onToggle: () -> Unit
) {

    val isOn = device.status == DeviceStatus.ON

    val cardBackground by animateColorAsState(
        targetValue = if (isOn) CardOnGradientStart else CardOffSurface,
        animationSpec = tween(durationMillis = 400),
        label = "cardBg"
    )

    val iconTint by animateColorAsState(
        targetValue = if (isOn) PrimaryTealLight else TextMuted,
        animationSpec = tween(durationMillis = 400),
        label = "iconTint"
    )

    val iconBgColor by animateColorAsState(
        targetValue = if (isOn) PrimaryTeal.copy(alpha = 0.2f) else DarkCardElevated,
        animationSpec = tween(durationMillis = 400),
        label = "iconBg"
    )


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isOn) 6.dp else 2.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {

                // Icon with circular background
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(iconBgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = getDeviceIcon(device.type),
                        contentDescription = device.name,
                        tint = iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = device.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    StatusBadge(status = device.status)

                    // Show AC details only for AC devices
                    if (device.type == DeviceType.AC && isOn) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "🌡 ${device.temperature}°C  •  🌀 ${device.fanSpeed}",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            }


            Switch(
                checked = isOn,
                onCheckedChange = { onToggle() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = PrimaryTeal,
                    checkedBorderColor = PrimaryTeal,
                    uncheckedThumbColor = TextMuted,
                    uncheckedTrackColor = DarkCardElevated,
                    uncheckedBorderColor = TextMuted
                )
            )
        }
    }
}



fun getDeviceIcon(
    type: DeviceType
): ImageVector = when (type) {
    DeviceType.LIGHT -> Icons.Default.Lightbulb
    DeviceType.SWITCH -> Icons.Default.Power
    DeviceType.AC -> Icons.Default.AcUnit
    DeviceType.IRON -> Icons.Default.LocalFireDepartment
    DeviceType.CAMERA -> Icons.Default.CameraAlt
}



fun getStatusText(
    status: DeviceStatus
): String {
    return when (status) {
        DeviceStatus.ON -> "ON"
        DeviceStatus.OFF -> "OFF"
        DeviceStatus.ERROR -> "ERROR"
        DeviceStatus.DISCONNECTED -> "DISCONNECTED"
    }
}