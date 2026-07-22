package com.example.smarthome.utils

import kotlinx.coroutines.delay


suspend fun startTimer(
    minutes: Int,
    onTick: (String) -> Unit,
    onFinish: () -> Unit
) {

    var remainingSeconds = minutes * 60


    while (remainingSeconds > 0) {


        val minutesLeft = remainingSeconds / 60

        val secondsLeft = remainingSeconds % 60


        val time =
            String.format(
                "%02d:%02d",
                minutesLeft,
                secondsLeft
            )


        onTick(time)


        delay(1000)


        remainingSeconds--

    }


    onTick("00:00")


    // Timer finished
    onFinish()

}