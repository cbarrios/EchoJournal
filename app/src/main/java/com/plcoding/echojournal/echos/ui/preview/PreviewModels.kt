package com.plcoding.echojournal.echos.ui.preview

import com.plcoding.echojournal.echos.ui.echos.models.PlaybackState
import com.plcoding.echojournal.echos.ui.models.EchoUi
import com.plcoding.echojournal.echos.ui.models.MoodUi
import java.time.Instant
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

data object PreviewModels {

    val echoUi = EchoUi(
        id = 1,
        title = "My audio memo",
        mood = MoodUi.PEACEFUL,
        recordedAt = Instant.now(),
        note = (1..50).joinToString(" ") { "Hello" },
        topics = listOf("Love", "Work"),
        amplitudes = (1..30).map { Random.nextFloat() },
        playbackTotalDuration = 250.seconds,
        playbackCurrentDuration = 120.seconds,
        playbackState = PlaybackState.PAUSED
    )
}
