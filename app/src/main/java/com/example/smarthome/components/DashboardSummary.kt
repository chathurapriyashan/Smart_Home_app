package com.example.smarthome.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smarthome.model.Device
import com.example.smarthome.model.DeviceStatus


@Composable
fun DashboardSummary(
    devices: List<Device>
) {


    val onDevices = devices.count {

        it.status == DeviceStatus.ON

    }


    val offDevices = devices.count {

        it.status == DeviceStatus.OFF

    }



    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )

    ) {


        Row(

            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),

            horizontalArrangement = Arrangement.SpaceBetween

        ) {


            Text(

                text = "🟢 ON: $onDevices",

                style = MaterialTheme.typography.titleMedium

            )


            Text(

                text = "⚪ OFF: $offDevices",

                style = MaterialTheme.typography.titleMedium

            )


        }

    }

}