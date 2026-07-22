package com.example.smarthome.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun TimerCard(

    title: String = "🔥 Bedroom Iron"

) {


    var minutes by remember {
        mutableStateOf("")
    }


    var remainingTime by remember {
        mutableStateOf("00:00")
    }



    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )

    ) {


        Column(

            modifier = Modifier
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {


            Text(
                text = title
            )


            Text(
                text = "Status: 🟢 ON"
            )


            OutlinedTextField(

                value = minutes,

                onValueChange = {
                    minutes = it
                },

                label = {
                    Text("Set Timer (Minutes)")
                }

            )


            Text(
                text = "Remaining Time: $remainingTime"
            )


            Row(

                horizontalArrangement = Arrangement.spacedBy(12.dp)

            ) {


                Button(

                    onClick = {

                        // Timer start logic will be added later

                    }

                ) {

                    Text("START")

                }



                Button(

                    onClick = {

                        // Stop timer logic will be added later

                    }

                ) {

                    Text("STOP")

                }


            }


        }

    }

}