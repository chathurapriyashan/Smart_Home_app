package com.example.smarthome.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthome.model.DeviceStatus


@Composable
fun StatusBadge(
    status: DeviceStatus
) {


    val (text, color) = when(status) {


        DeviceStatus.ON -> {
            "🟢 ON" to Color(0xFF2E7D32)
        }


        DeviceStatus.OFF -> {
            "⚪ OFF" to Color(0xFF757575)
        }


        DeviceStatus.ERROR -> {
            "🔴 ERROR" to Color(0xFFC62828)
        }


        DeviceStatus.DISCONNECTED -> {
            "⚫ DISCONNECTED" to Color(0xFF424242)
        }

    }



    Text(

        text = text,

        color = Color.White,

        fontSize = 14.sp,

        modifier = Modifier

            .background(

                color = color,

                shape = RoundedCornerShape(50)

            )

            .padding(

                horizontal = 12.dp,

                vertical = 6.dp

            )

    )

}