package com.example.smarthome.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


private val LightColors = lightColorScheme(

    primary = PrimaryGreen,

    secondary = SecondaryGreen,

    background = BackgroundLight,

    surface = CardWhite

)



private val DarkColors = darkColorScheme(

    primary = PrimaryGreen,

    secondary = SecondaryGreen

)



@Composable
fun SmartHomeTheme(

    darkTheme: Boolean = isSystemInDarkTheme(),

    content: @Composable () -> Unit

) {


    val colors = if(darkTheme) {

        DarkColors

    } else {

        LightColors

    }



    MaterialTheme(

        colorScheme = colors,

        typography = Typography(),

        content = content

    )

}