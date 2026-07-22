package com.example.smarthome.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SwitchCard(

    title: String = "3-Switch Unit"

) {


    var switch1 by remember {
        mutableStateOf(false)
    }


    var switch2 by remember {
        mutableStateOf(false)
    }


    var switch3 by remember {
        mutableStateOf(false)
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
                .padding(16.dp)

        ) {


            Text(
                text = "🔌 $title"
            )



            SwitchRow(

                name = "Switch 1",

                checked = switch1,

                onChanged = {
                    switch1 = it
                }

            )



            SwitchRow(

                name = "Switch 2",

                checked = switch2,

                onChanged = {
                    switch2 = it
                }

            )



            SwitchRow(

                name = "Switch 3",

                checked = switch3,

                onChanged = {
                    switch3 = it
                }

            )

        }

    }

}




@Composable
fun SwitchRow(

    name: String,

    checked: Boolean,

    onChanged: (Boolean) -> Unit

) {


    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),

        verticalAlignment = Alignment.CenterVertically

    ) {


        Text(

            text = name,

            modifier = Modifier.weight(1f)

        )


        Switch(

            checked = checked,

            onCheckedChange = onChanged

        )


    }

}