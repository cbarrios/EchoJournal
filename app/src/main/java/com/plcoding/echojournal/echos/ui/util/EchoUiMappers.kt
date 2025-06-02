package com.plcoding.echojournal.echos.ui.util

import com.plcoding.echojournal.echos.domain.echo.Echo
import com.plcoding.echojournal.echos.ui.echos.models.PlaybackState
import com.plcoding.echojournal.echos.ui.models.EchoUi
import com.plcoding.echojournal.echos.ui.models.MoodUi
import kotlin.time.Duration

fun Echo.toEchoUi(
    currentPlaybackDuration: Duration = Duration.ZERO,
    playbackState: PlaybackState = PlaybackState.STOPPED
): EchoUi {
    return EchoUi(
        id = id!!,
        title = title,
        mood = MoodUi.valueOf(mood.name),
        recordedAt = recordedAt,
        note = note,
        topics = topics,
        amplitudes = audioAmplitudes,
        playbackTotalDuration = audioPlaybackLength,
        audioFilePath = audioFilePath,
        playbackCurrentDuration = currentPlaybackDuration,
        playbackState = playbackState
    )
}