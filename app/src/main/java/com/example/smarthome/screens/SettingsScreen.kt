package com.example.smarthome.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SettingsScreen() {


    var safeDuration by remember {
        mutableStateOf("")
    }


    var appliedDuration by remember {
        mutableStateOf("15")
    }



    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(
                rememberScrollState()
            ),

        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {


        Text(
            text = "⚙ Settings",
            style = MaterialTheme.typography.headlineSmall
        )



        // Floor Plan Section

        Text(
            text = "🏠 Floor Plan",
            style = MaterialTheme.typography.titleLarge
        )



        Card(
            modifier = Modifier
                .fillMaxSize(),

            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )

        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {


                Text(
                    text = "Ground Floor"
                )


                Text(
                    text = """
                    
                    +----------------+
                    | Living Room    |
                    | Kitchen        |
                    | Hall           |
                    +----------------+
                    
                    Floor Plan Image
                    (Add Image Later)
                    
                    """.trimIndent()
                )

            }

        }




        Card(

            modifier = Modifier.fillMaxSize(),

            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )

        ) {


            Column(
                modifier = Modifier.padding(20.dp)
            ) {


                Text(
                    text = "First Floor"
                )


                Text(
                    text = """
                    
                    +----------------+
                    | Bedroom        |
                    | Hallway        |
                    | Iron Area      |
                    +----------------+
                    
                    Floor Plan Image
                    (Add Image Later)
                    
                    """.trimIndent()
                )

            }

        }




        // Safe Duration

        Text(
            text = "🔥 Maximum Safe Duration",
            style = MaterialTheme.typography.titleLarge
        )


        Text(
            text = "Current Duration : $appliedDuration Minutes"
        )



        TextField(

            value = safeDuration,

            onValueChange = {
                safeDuration = it
            },

            label = {
                Text(
                    "Enter Minutes"
                )
            }

        )



        Button(

            onClick = {

                if(safeDuration.isNotEmpty()){

                    appliedDuration = safeDuration

                }

            }

        ) {


            Text(
                "APPLY"
            )

        }



        Text(
            text = """
            
            About Home
            
            Smart Home Monitoring System
            Version 1.0
            
            """
        )


    }

}