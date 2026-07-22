package com.example.smarthome.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smarthome.model.Device
import com.example.smarthome.model.DeviceStatus
import com.example.smarthome.model.DeviceType



@Composable
fun DeviceCard(
    device: Device,
    onToggle: () -> Unit
) {


    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),

        shape = RoundedCornerShape(20.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )

    ) {


        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.SpaceBetween

        ) {



            Row(

                verticalAlignment = Alignment.CenterVertically

            ) {


                Icon(

                    imageVector = getDeviceIcon(device.type),

                    contentDescription = device.name,

                    modifier = Modifier.padding(end = 16.dp)

                )



                Column {


                    Text(
                        text = device.name,
                        style = MaterialTheme.typography.titleMedium
                    )


                    StatusBadge(
                        status = device.status
                    )


                    // Show AC details only for AC devices

                    if(device.type == DeviceType.AC) {


                        Text(
                            text = "🌡 Temperature: ${device.temperature}°C"
                        )


                        Text(
                            text = "🌀 Fan Speed: ${device.fanSpeed}"
                        )

                    }

                }

            }



            Switch(

                checked = device.status == DeviceStatus.ON,

                onCheckedChange = {

                    onToggle()

                }

            )

        }

    }

}



fun getDeviceIcon(
    type: DeviceType
) = when(type) {


    DeviceType.LIGHT ->
        Icons.Default.Lightbulb


    DeviceType.SWITCH ->
        Icons.Default.Power


    DeviceType.AC ->
        Icons.Default.AcUnit


    DeviceType.IRON ->
        Icons.Default.Warning


    DeviceType.CAMERA ->
        Icons.Default.CameraAlt

}



fun getStatusText(
    status: DeviceStatus
): String {


    return when(status) {


        DeviceStatus.ON ->
            "🟢 ON"


        DeviceStatus.OFF ->
            "⚪ OFF"


        DeviceStatus.ERROR ->
            "🔴 ERROR"


        DeviceStatus.DISCONNECTED ->
            "⚫ DISCONNECTED"

    }

}