package com.plcoding.echojournal.echos.ui.util

import com.plcoding.echojournal.app.navigation.NavigationRoute
import com.plcoding.echojournal.echos.domain.recording.RecordingDetails
import kotlin.time.Duration.Companion.milliseconds

fun RecordingDetails.toCreateEchoRoute(): NavigationRoute.CreateEcho {
    return NavigationRoute.CreateEcho(
        recordingPath = filePath ?: throw IllegalArgumentException("Recording path can't be null."),
        duration = duration.inWholeMilliseconds,
        amplitudes = amplitudes.joinToString(";")
    )
}

fun NavigationRoute.CreateEcho.toRecordingDetails(): RecordingDetails {
    return RecordingDetails(
        filePath = recordingPath,
        duration = duration.milliseconds,
        amplitudes = amplitudes.split(";").map { it.toFloat() }
    )
}